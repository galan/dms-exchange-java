package de.galan.dmsexchange.poc;

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class ZipTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		File file = new File("/home/daniel/temp/test.zip");
		File fileIn = new File("/home/daniel/temp/dummy.txt");
		//ZipFile zf = new ZipFile(file, ZipFile.OPEN_READ, Charsets.UTF_8);
		//zf.close();
		//ZipUtil.
		Map<String, String> env = new HashMap<>();
		env.put("create", "true");
		// locate file system by using the syntax
		// defined in java.net.JarURLConnection
		//URI uri = URI.create("jar:file:/home/daniel/temp/zipfstest.zip");
		URI uri = URI.create("jar:" + file.toURI());

		try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
			//Path externalTxtFile = Paths.get("/codeSamples/zipfs/SomeTextFile.txt");
			//Paths.
			Path pathInZipfile = zipfs.getPath("/SomeTextFile.txt");
			// copy a file into the zip file
			Files.copy(fileIn.toPath(), pathInZipfile, StandardCopyOption.REPLACE_EXISTING);
		}
	}

}
