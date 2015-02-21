package de.galan.dmsexchange.exchange.read;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.Consumer;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.exchange.container.ContainerDeserializer;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.verjson.util.ReadException;


/**
 * Reads all document-containers inside the given export-archive. The read document-containers will be posted to the
 * registered listeners.
 *
 * @author daniel
 */
public class DefaultDmsReader extends DefaultExchange implements DmsReader {

	private static final Logger LOG = Logr.get();
	private InputStream inputstream;
	private ContainerDeserializer deserializer;


	public DefaultDmsReader(InputStream inputstream) {
		this.inputstream = inputstream;
		deserializer = new ContainerDeserializer();
	}


	@Override
	public void registerListener(Object... listeners) {
		Arrays.asList(listeners).stream().forEach(this::registerListener);
	}


	@Override
	public void readDocuments(Consumer<Document> consumer) throws DmsExchangeException {
		if (consumer == null) {
			throw new NullPointerException("Consumer is null");
		}
		WrappingDocumentConsumer wrapper = new WrappingDocumentConsumer(consumer);
		registerListener(wrapper);
		try {
			readDocuments();
		}
		finally {
			unregisterListener(wrapper);
		}
	}


	@Override
	public void readDocuments() throws DmsExchangeException {
		// iterate over directories recursivly, this blocks until finished
		CountingDocumentConsumer counter = new CountingDocumentConsumer();
		registerListener(counter);
		try {
			readArchive();
		}
		finally {
			unregisterListener(counter);
		}
		LOG.info("Finished reading export-archive with {} documents", counter.getCountedDocuments());
	}


	private void readArchive() {
		try (TarArchiveInputStream tar = new TarArchiveInputStream(new GzipCompressorInputStream(inputstream))) {
			TarArchiveEntry entry = null;
			while((entry = tar.getNextTarEntry()) != null) {
				if (!entry.isDirectory()) {
					if (entry.getName().endsWith(".tgz") || entry.getName().endsWith(".tar.gz")) {
						//byte[] baos = IOUtils.toByteArray(tar);
						try {
							Document document = deserializer.unarchive(tar, false);
							postEvent(document);
						}
						catch (DmsExchangeException ex) {
							//TODO
						}
						catch (ReadException ex) {
							// TODO
						}
					}
					else {
						LOG.warn("Unrecognized element: {}", entry.getName());
					}
				}
			}
		}
		catch (FileNotFoundException ex) {
			//Say.warn("Unspecified error from daniel", ex);
		}
		catch (IOException ex) {
			//Say.warn("Unspecified error from daniel", ex);
		}
	}

	/*

	private File initTempDir() throws DmsExchangeException {
		File dirSystemTemp = new File(System.getProperty("java.io.tmpdir"));
		File result = new File(dirSystemTemp, "dms-exchange");
		result.mkdirs();
		if (!result.exists()) {
			throw new DmsExchangeException("Unable to create intermediate temp-directory '" + result.getAbsolutePath() + "'");
		}
		return result;
	}


	private void traverseDirectory(String directory) throws DmsExchangeException {
		try {
			List<String> files = getFs().listFiles(directory);
			boolean root = directory.equals("/");
			if (!root && files.stream().anyMatch(f -> endsWith(f, "/meta.json"))) {
				importDocumentContainerDirectory(directory);
			}
			else {
				for (String file: files) {
					if (endsWith(file, "/")) {
						traverseDirectory(file);
					}
					else if (endsWith(file, ".tar")) {
						importDocumentContainerFile(file);
					}
					else if (root && StringUtils.equals(file, "/export.json")) {
						// ignore
					}
					else {
						postEvent(new DocumentReadInvalidEvent(file));
						LOG.warn("Unrecognized filetype: " + file);
					}
				}
			}
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to list files in archive", ex);
		}
	}


	protected File getTempZipFile() {
		String uuid = UUID.randomUUID().toString();
		return new File(dirTemp, uuid + ".zip");
	}


	protected void importDocumentContainerFile(String file) throws DmsExchangeException {
		LOG.info("Importing file: " + file);
		// extract to tmp
		File fileTemp = getTempZipFile();
		try {
			getFs().readFile(file, new FileOutputStream(fileTemp));
			try (ArchiveFileSystem zipFs = new NioZipFileSystem(fileTemp, true)) {
				importDocumentContainer(zipFs, "/");
			}
		}
		catch (IOException ex) {
			LOG.warn("Unable to read intermediate zip file '" + fileTemp.getAbsolutePath() + "'", ex);
		}
		finally {
			if (!FileUtils.deleteQuietly(fileTemp)) {
				LOG.warn("Unable to delete intermediate zip file '" + fileTemp.getAbsolutePath() + "'");
			}
		}

	}


	protected void importDocumentContainerDirectory(String directory) throws DmsExchangeException {
		LOG.info("Importing directory: " + directory);
		importDocumentContainer(getFs(), directory);
	}


	protected void importDocumentContainer(ArchiveFileSystem afs, String directory) throws DmsExchangeException {
		try {
			String metaJson = afs.readFileAsString(directory + "meta.json");
			JsonNode node = getVerjsonExport().readTree(metaJson);
			long version = determineVersion(node);
			Document document = getVerjsonDocument().readPlain(node, version);

			for (DocumentFile df: document.getDocumentFiles()) {
				for (Revision revision: df.getRevisions()) {
					String generated = revision.getTsAdded().format(FORMATTER) + "_" + df.getFilename();
					revision.setData(afs.readFile(directory + "revisions/" + generated));
				}
			}
			postEvent(document);
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Could not extract data from document container", ex);
		}
		catch (ReadException ex) {
			throw new DmsExchangeException("Could not deserialize metadata from document container", ex);
		}
	}


	protected long determineVersion(JsonNode exportNode) throws InvalidArchiveException {
		// evaluate version string from export json
		String version = null;
		try {
			version = obj(exportNode).get("version").asText();
		}
		catch (Exception ex) {
			throw new InvalidArchiveException("Unable to determine version from export.json");
		}
		// evaluate mapped version for verjson
		Long result = Version.getVerjson(version);
		if (result == null) {
			throw new InvalidArchiveException("Version '" + version + "' not supported");
		}
		return result;
	}
	 */

}
