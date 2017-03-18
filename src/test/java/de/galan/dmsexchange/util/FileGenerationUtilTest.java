package de.galan.dmsexchange.util;

import static de.galan.commons.test.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT FileGenerationUtil
 */
public class FileGenerationUtilTest extends AbstractTestParent {

	@Test
	public void fileInExistingDirectory() throws Exception {
		File file = new File(getTestDirectory(true), "file-in-directory");
		assertPreparedFile(file);
	}


	@Test
	public void fileInNotExistingDirectory() throws Exception {
		File file = new File(getTestDirectory(true), "subdir1/subdir2/file-in-subdirectory");
		assertPreparedFile(file);
	}


	@Test
	public void existingFile() throws Exception {
		File file = new File(getTestDirectory(true), "file-in-directory");
		FileUtils.touch(file);
		assertPreparedFile(file);
	}


	protected void assertPreparedFile(File file) {
		File preparedFile = FileGenerationUtil.prepareFile(file);
		assertThat(preparedFile.getParentFile()).exists();
		assertThat(preparedFile.getParentFile()).isDirectory();
	}

}
