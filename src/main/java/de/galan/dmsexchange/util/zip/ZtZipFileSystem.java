package de.galan.dmsexchange.util.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.zeroturnaround.zip.ZipUtil;


/**
 * Access to a zip-file using <a href="https://github.com/zeroturnaround/zt-zip">zt-zip</a>. Seems to create a lot of
 * overhead, since the zip file is copied, modified and then copied back on each added file. Accessing the file manually
 * did not worked and unit-tests failed, too.
 *
 * @author daniel
 */
public class ZtZipFileSystem implements ArchiveFileSystem {

	private File file;
	private boolean readonly;


	public ZtZipFileSystem(File file, boolean readonly) throws IOException {
		this.file = file;
		this.readonly = readonly;
		verifyFile();
	}


	protected boolean isReadonly() {
		return readonly;
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
		if (!file.exists()) {
			new ZipOutputStream(new FileOutputStream(file)).close();
			//ZipFile zip = new ZipFile(file);
			//zip.close();
		}
	}


	/** Closes the zip file */
	@Override
	public void close() throws IOException {
		// noop
	}


	@Override
	public void addFile(String filename, byte[] data) throws IOException {
		ensureWritable();
		if (ZipUtil.containsEntry(file, filename)) {
			throw new IOException("File '" + filename + "' does already exist");
		}
		ZipUtil.addEntry(file, filename, data);
	}


	@Override
	public List<String> listFiles(String directory) throws IOException {
		return null;
	}


	@Override
	public byte[] readFile(String filename) throws IOException {
		return null;
	}


	@Override
	public void readFile(String file, OutputStream stream) throws IOException {
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
