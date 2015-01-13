package de.galan.dmsexchange.util.zip;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT NioZipFileSystem
 *
 * @author daniel
 */
public class NioZipFileSystemTest extends AbstractTestParent {

	@Test
	public void listFiles() throws Exception {
		URI uri = getClass().getResource("example.zip").toURI();
		File file = new File(uri);
		NioZipFileSystem fs = new NioZipFileSystem(file, true);
		List<String> files = fs.listFiles("/");
		assertThat(files).containsOnly("/export.json", "/0000/");
		fs.close();
	}

}
