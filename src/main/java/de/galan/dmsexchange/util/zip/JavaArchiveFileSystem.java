package de.galan.dmsexchange.util.zip;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipFile;


/**
 * Implementation using Javas default build-in zip classes.
 *
 * @author daniel
 */
public class JavaArchiveFileSystem implements ArchiveFileSystem {

	private File file;
	private boolean readonly;
	private ZipFile zip;


	public JavaArchiveFileSystem(File file, boolean readonly) throws IOException {
		this.file = file;
		this.readonly = readonly;
		//mountFile();
	}


	protected boolean isReadonly() {
		return readonly;
	}


	protected void mountFile() throws IOException {
		zip = new ZipFile(file);

		/*
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
		 */
	}


	@Override
	public void close() throws IOException {
		zip.close();
	}


	@Override
	public void addFile(String file, byte[] data) throws IOException {

	}


	@Override
	public List<String> listFiles(String directory) throws IOException {
		return null;
	}


	@Override
	public void readFile(String file, OutputStream stream) throws IOException {
	}

}
