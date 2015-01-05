package de.galan.dmsexchange;

import static de.galan.verjson.util.Transformations.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.eventbus.EventBus;

import de.galan.commons.logging.Logr;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.meta.export.Export;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.InvalidArchiveException;
import de.galan.dmsexchange.util.Version;
import de.galan.dmsexchange.verjson.document.DocumentVersions;
import de.galan.dmsexchange.verjson.export.ExportVersions;
import de.galan.verjson.core.Verjson;


/**
 * Prototyping, will be removed soon
 *
 * @author daniel
 */
public class DmsExchangePrototype {

	private static final Logger LOG = Logr.get();

	EventBus events;
	File file;
	FileSystem fs;
	private Verjson<Export> verjsonExport;
	private Verjson<Document> verjsonDocument;
	Export export;


	public DmsExchangePrototype(String file) throws DmsExchangeException {
		this(new File(file));
	}


	public DmsExchangePrototype(File file) throws DmsExchangeException {
		this.file = file;
		init();
	}


	/** Closes the zip file and writes the export-meta data */
	public void close() throws DmsExchangeException {
		if (fs != null && fs.isOpen()) {
			try {
				String exportJson = verjsonExport.writePlain(export);
				Path pathExportJson = fs.getPath("/export.json");
				// delete if exists
				Files.deleteIfExists(pathExportJson);
				// write txt
				try (BufferedWriter writer = Files.newBufferedWriter(pathExportJson, Charsets.UTF_8)) {
					writer.write(exportJson, 0, exportJson.length());
				}
			}
			catch (IOException exWrite) {
				throw new DmsExchangeException("Unable to write export metadata", exWrite);
			}
			finally {
				try {
					fs.close();
				}
				catch (IOException exClose) {
					throw new DmsExchangeException("Unable to close export archive", exClose);
				}
			}
		}
	}


	protected void init() throws DmsExchangeException {
		events = new EventBus("dms-exchange");
		verjsonExport = Verjson.create(Export.class, new ExportVersions());
		verjsonDocument = Verjson.create(Document.class, new DocumentVersions());
		mountFile();
	}


	private void mountFile() throws DmsExchangeException {
		Map<String, String> env = new HashMap<>();
		env.put("create", "true");
		boolean existingArchive = file.exists() && file.isFile();

		try {
			// locate file system by using the syntax defined in java.net.JarURLConnection
			URI uri = URI.create("jar:" + file.toURI());
			fs = FileSystems.newFileSystem(uri, env);
		}
		catch (IOException ex) {
			throw new DmsExchangeException("zip file '" + file.getAbsolutePath() + "' could not be opened.", ex);
		}

		if (existingArchive) {
			try {
				Path pathExportJson = fs.getPath("/export.json");
				boolean pathExportJsonExists = Files.isRegularFile(pathExportJson) & Files.isReadable(pathExportJson);
				if (!pathExportJsonExists) {
					throw new DmsExchangeException("export.json does not exist");
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Files.copy(pathExportJson, baos);
				String exportJson = baos.toString(Charsets.UTF_8.name());
				JsonNode exportNode = verjsonExport.readTree(exportJson);
				export = verjsonExport.readPlain(exportNode, determineVersion(exportNode)); // TODO read version from node and perform mapping
				LOG.info("Read archive from '" + defaultString(export.getSourceAsString(), "unknown source") + "' exported on " + export.getTsExport());
			}
			catch (Exception ex) {
				throw new InvalidArchiveException("Export-metadata could not be read", ex);
			}
		}
		else {
			export = new Export();
		}
	}


	// TODO refactor out
	protected long determineVersion(JsonNode exportNode) throws InvalidArchiveException {
		ObjectNode node = getObj(obj(exportNode), "version");
		String version = node.asText();
		Long result = Version.getVerjson(version);
		if (result == null) {
			throw new InvalidArchiveException("Version '" + version + "' not supported");
		}
		return result;
	}


	public void registerListener(Object object) {
		events.register(object);
	}

}
