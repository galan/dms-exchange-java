package de.galan.dmsexchange.exchange.read;

import static de.galan.verjson.util.Transformations.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.commons.logging.Logr;
import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.meta.document.DocumentFile;
import de.galan.dmsexchange.meta.document.Revision;
import de.galan.dmsexchange.meta.export.Export;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.InvalidArchiveException;
import de.galan.dmsexchange.util.Version;
import de.galan.dmsexchange.util.zip.ArchiveFileSystem;
import de.galan.dmsexchange.util.zip.NioZipFileSystem;
import de.galan.verjson.util.ReadException;


/**
 * Reads all document-containers inside the given export-archive. The read document-containers will be posted to the
 * registered listeners.
 *
 * @author daniel
 */
public class DefaultDmsReader extends DefaultExchange implements DmsReader {

	private static final Logger LOG = Logr.get();
	private File dirTemp;


	public DefaultDmsReader(File file) throws DmsExchangeException {
		super(file, true);
		dirTemp = initTempDir();
	}


	protected File initTempDir() throws DmsExchangeException {
		File dirSystemTemp = new File(System.getProperty("java.io.tmpdir"));
		File result = new File(dirSystemTemp, "dms-exchange");
		result.mkdirs();
		if (!result.exists()) {
			throw new DmsExchangeException("Unable to create intermediate temp-directory '" + result.getAbsolutePath() + "'");
		}
		return result;
	}


	@Override
	public void readDocuments(Consumer<Document> consumer) throws DmsExchangeException {
		if (consumer == null) {
			throw new NullPointerException("Consumer is null");
		}
		WrappingDocumentConsumer wrapper = new WrappingDocumentConsumer(consumer);
		registerListener(wrapper);
		readDocuments();
		unregisterListener(wrapper);
	}


	@Override
	public void readDocuments() throws DmsExchangeException {
		Export export = readExportJson();
		Integer successful = export.getDocumentsSuccessfulAmount();
		LOG.info("Reading export-archive from source '{}' with {} documents exported at '{}'", export.getSourceAsString(), successful == null ? "unknown"
				: successful, export.getTsExport());
		// TODO check if listeners are registered (correct ones, otherwise DeadEvents will be send out)

		// iterate over directories recursivly, this blocks until finished
		CountingDocumentConsumer counter = new CountingDocumentConsumer();
		registerListener(counter);
		traverseDirectory("/", export);
		unregisterListener(counter);
		LOG.info("Finished reading export-archive with {} documents", counter.getCountedDocuments());
	}


	protected void traverseDirectory(String directory, Export export) throws DmsExchangeException {
		try {
			List<String> files = getFs().listFiles(directory);
			boolean root = directory.equals("/");
			if (!root && files.stream().anyMatch(f -> endsWith(f, "/meta.json"))) {
				importDocumentContainerDirectory(directory, export);
			}
			else {
				for (String file: files) {
					if (endsWith(file, "/")) {
						traverseDirectory(file, export);
					}
					else if (endsWith(file, ".zip")) {
						importDocumentContainerFile(file, export);
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


	protected void importDocumentContainerFile(String file, Export export) throws DmsExchangeException {
		LOG.info("Importing file: " + file);
		// extract to tmp
		File fileTemp = getTempZipFile();
		try {
			getFs().readFile(file, new FileOutputStream(fileTemp));
			try (ArchiveFileSystem zipFs = new NioZipFileSystem(fileTemp, true)) {
				importDocumentContainer(zipFs, "/", export);
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


	protected void importDocumentContainerDirectory(String directory, Export export) throws DmsExchangeException {
		LOG.info("Importing directory: " + directory);
		importDocumentContainer(getFs(), directory, export);
	}


	protected void importDocumentContainer(ArchiveFileSystem afs, String directory, Export export) throws DmsExchangeException {
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


	protected Export readExportJson() throws DmsExchangeException {
		try {
			String exportJson = getFs().readFileAsString("/export.json");
			JsonNode node = getVerjsonExport().readTree(exportJson);
			return getVerjsonExport().readPlain(node, determineVersion(node));
		}
		catch (Exception ex) {
			throw new DmsExchangeException("Invalid Archiv, no valid export.json found.", ex);
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


	@Override
	public void registerListener(Object... listeners) {
		Arrays.asList(listeners).stream().forEach(this::registerListener);
	}

}
