package de.galan.dmsexchange.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class ZipFileSystem {

	FileSystem fs;
	private File file;
	private boolean readonly;


	public ZipFileSystem(File file, boolean readonly) throws IOException {
		this.file = file;
		this.readonly = readonly;
		mountFile();
	}


	protected void mountFile() throws IOException {
		verifyFile();

		Map<String, String> env = new HashMap<>();
		if (!readonly) {
			env.put("create", "true");
		}

		try {
			// locate file system by using the syntax defined in java.net.JarURLConnection
			URI uri = URI.create("jar:" + file.toURI());
			fs = FileSystems.newFileSystem(uri, env);
		}
		catch (IOException ex) {
			throw new IOException("Unable to to " + (readonly ? "read" : "create") + " zip file", ex);
		}
	}


	public void writeTextFile(String absoluteFilePath, String exportJson) throws IOException {
		Path pathExportJson = fs.getPath(absoluteFilePath);
		Files.deleteIfExists(pathExportJson);
		// write txt
		try (BufferedWriter writer = Files.newBufferedWriter(pathExportJson, Charsets.UTF_8)) {
			writer.write(exportJson, 0, exportJson.length());
		}
	}


	public void readFile() {
		/*
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
		 */
	}


	private boolean verifyFile() throws IOException {
		boolean fileExists = file.exists() && file.isFile();

		if (readonly) {
			if (!fileExists) {
				throw new IOException("File does not exist");
			}
		}
		else {
			if (fileExists) {
				throw new IOException("File does already exist");
			}
		}
		return fileExists;
	}


	/** Closes the zip file */
	public void close() throws IOException {
		if (fs != null && fs.isOpen()) {
			fs.close();
		}
	}

}
