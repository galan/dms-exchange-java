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
		List<String> filesRoot = fs.listFiles("/");
		assertThat(filesRoot).containsOnly("/export.json", "/0000/");
		List<String> filesDir1 = fs.listFiles("/0000/");
		assertThat(filesDir1).containsOnly("/0000/0000/");
		List<String> filesDir2 = fs.listFiles("/0000/0000/");
		assertThat(filesDir2).containsOnly("/0000/0000/meta.json", "/0000/0000/revisions/");
		List<String> filesDir3 = fs.listFiles("/0000/0000/revisions/");
		assertThat(filesDir3).containsOnly("/0000/0000/revisions/20150104T175952Z_third.doc", "/0000/0000/revisions/20150103T175942Z_third.doc",
			"/0000/0000/revisions/20141230T081011Z_third.doc", "/0000/0000/revisions/20150103T175812Z_second.doc",
			"/0000/0000/revisions/20141230T091030Z_second.doc", "/0000/0000/revisions/20150105T123710Z_first.pdf",
			"/0000/0000/revisions/20150105T123032Z_first.pdf");
		fs.close();
	}

}
