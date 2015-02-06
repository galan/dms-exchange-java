package de.galan.dmsexchange.util.zip;

import static java.util.stream.Collectors.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


/**
 * Access to a zip-file with nio using the <a
 * href="https://docs.oracle.com/javase/7/docs/technotes/guides/io/fsp/zipfilesystemprovider.html">Zip File System
 * Provider</a>. See also the <a
 * href="https://blogs.oracle.com/xuemingshen/entry/the_zip_filesystem_provider_in1">related blog-entry</a>.
 *
 * @author daniel
 */
public class NioZipFileSystem implements ArchiveFileSystem {

	private static final FilelistComparator FILELIST_COMPARATOR = new FilelistComparator();

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


	@Override
	public List<String> listFiles(String directory) throws IOException {
		List<String> result = null;
		Path path = fs.getPath(directory);
		try (Stream<Path> stream = Files.list(path)) {
			result = stream.map(p -> p.toString()).collect(toList());
		}
		result.sort(FILELIST_COMPARATOR);
		return result;
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


	@Override
	public byte[] readFile(String filename) throws IOException {
		Path path = fs.getPath(filename);
		if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			throw new IOException("File '" + filename + "' does not exist");
		}
		return Files.readAllBytes(path);
	}


	@Override
	public void readFile(String filename, OutputStream stream) throws IOException {
		Path path = fs.getPath(filename);
		if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			throw new IOException("File '" + filename + "' does not exist");
		}
		Files.copy(path, stream);
	}

}
