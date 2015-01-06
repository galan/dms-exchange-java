package de.galan.dmsexchange.util.zip;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;


/**
 * Access to a zip-file with nio using the <a
 * href="https://docs.oracle.com/javase/7/docs/technotes/guides/io/fsp/zipfilesystemprovider.html">Zip File System
 * Provider</a>. See also the <a
 * href="https://blogs.oracle.com/xuemingshen/entry/the_zip_filesystem_provider_in1">related blog-entry</a>.
 *
 * @author daniel
 */
public class NioZipFileSystem implements ArchiveFileSystem {

	FileSystem fs;
	private File file;
	private boolean readonly;


	public NioZipFileSystem(File file, boolean readonly) throws IOException {
		this.file = file;
		this.readonly = readonly;
		mountFile();
	}


	protected boolean isReadonly() {
		return readonly;
	}


	protected void mountFile() throws IOException {
		verifyFile();

		Map<String, String> env = new HashMap<>();
		if (!isReadonly()) {
			env.put("create", "true");
		}

		try {
			// locate file system by using the syntax defined in java.net.JarURLConnection
			URI uri = URI.create("jar:" + file.toURI());
			fs = FileSystems.newFileSystem(uri, env);
		}
		catch (IOException ex) {
			throw new IOException("Unable to to " + (isReadonly() ? "read" : "create") + " zip file", ex);
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


	public void readDirectory(String directory) {
		/*
		try {
			Path path = fs.getPath(directory);
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
		if (isReadonly()) {
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


	private void ensureWritable() throws IOException {
		if (isReadonly()) {
			throw new IOException("File is not opened as writable");
		}
	}


	/** Closes the zip file */
	@Override
	public void close() throws IOException {
		if (fs != null && fs.isOpen()) {
			fs.close();
		}
	}


	@Override
	public void addFile(String filename, byte[] data) throws IOException {
		ensureWritable();
		Path path = fs.getPath(filename);
		if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			throw new IOException("File '" + filename + "' does already exist");
		}
		Files.createDirectories(path.getParent());
		Files.write(path, data, StandardOpenOption.CREATE_NEW);
	}

	/*
	@Deprecated
	private void writeTextFile(String absoluteFilePath, String exportJson) throws IOException {
		Path pathExportJson = fs.getPath(absoluteFilePath);
		Files.deleteIfExists(pathExportJson);
		// write txt
		try (BufferedWriter writer = Files.newBufferedWriter(pathExportJson, Charsets.UTF_8)) {
			writer.write(exportJson, 0, exportJson.length());
		}
	}
	 */

}
