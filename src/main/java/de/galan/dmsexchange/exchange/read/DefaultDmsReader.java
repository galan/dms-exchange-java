package de.galan.dmsexchange.exchange.read;

import static de.galan.verjson.util.Transformations.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Charsets;

import de.galan.commons.logging.Logr;
import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.meta.export.Export;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.InvalidArchiveException;
import de.galan.dmsexchange.util.Version;
import de.galan.dmsexchange.util.zip.ArchiveFileSystem;
import de.galan.dmsexchange.util.zip.NioZipFileSystem;


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


	protected File getTempZipFile() {
		String uuid = UUID.randomUUID().toString();
		File file = new File(dirTemp, uuid + ".zip");
		return file;
	}


	@Override
	public void readDocuments() throws DmsExchangeException {
		Export export = readExportJson();
		LOG.info("reading ..."); // TODO add information found about source
		// TODO check if listeners are registered (correct ones, otherwise DeadEvents will be send out)

		// iterate over directories recursivly
		traverseDirectory("/");
	}


	protected void traverseDirectory(String directory) throws DmsExchangeException {
		try {
			List<String> files = getFs().listFiles(directory);
			if (directory.equals("/")) {
				// ?
			}
			if (files.stream().anyMatch(f -> endsWith(f, "/meta.json"))) {
				importDocumentContainerDirectory(directory);
			}
			else {
				for (String file: files) {
					if (endsWith(file, "/")) {
						traverseDirectory(file);
					}
					else if (endsWith(file, ".zip")) {
						importDocumentContainerFile(file);
					}
					else {
						LOG.warn("Unrecognized filetype: " + file);
					}
				}
			}
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to list files in archive", ex);
		}
	}


	protected void importDocumentContainerFile(String file) {
		LOG.info("Importing file: " + file);
		// extract to tmp
		File fileTemp = getTempZipFile();
		try {
			getFs().readFile(file, new FileOutputStream(fileTemp));
			ArchiveFileSystem zipFs = new NioZipFileSystem(fileTemp, true);
			String meta = zipFs.readFileAsString("/meta.json");
			LOG.info(meta);
			zipFs.close();
		}
		catch (IOException ex) {
			LOG.warn("Unspecified error from daniel", ex);
		}
		finally {
			if (!FileUtils.deleteQuietly(fileTemp)) {
				LOG.warn("Unable to delete intermediate zip file '" + fileTemp.getAbsolutePath() + "'");
			}
		}

	}


	protected void importDocumentContainerDirectory(String directory) {
		LOG.info("Importing directory: " + directory);
	}


	protected Export readExportJson() throws DmsExchangeException {
		try {
			String exportJson = new String(getFs().readFile("/export.json"), Charsets.UTF_8);
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
