package de.galan.dmsexchange.test;

import static de.galan.commons.test.Tests.*;
import static org.apache.commons.lang3.StringUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import de.galan.commons.logging.Logr;


/**
 * Helper for working with container and archive files.
 *
 * @author daniel
 */
public class Archives {

	public static File prepareArchive(File directory) throws IOException {
		File result = new File(getTestDirectory(true), "expected.tgz");
		// copy to test
		FileUtils.copyDirectory(directory, result);
		// remove placeholder for directories that should be able to exist in git
		Files.fileTreeTraverser().postOrderTraversal(result).filter(file -> file.getName().equals("git-empty-dir")).toList().forEach(FileUtils::deleteQuietly);
		// iterate over directories
		List<File> metas = Files.fileTreeTraverser().postOrderTraversal(result).filter(file -> file.getName().equals("meta.json")).toList();
		// tar directories with meta.json
		metas.stream().map(f -> f.getParentFile()).forEach(file -> {
			try {
				TarTests.unexplode(file, false);
			}
			catch (Exception ex) {
				throw new RuntimeException("Could not unexplose directory: " + file.getAbsolutePath(), ex);
			}
		});
		// tgz the whole directory
		TarTests.unexplode(result, true);
		return result;
	}


	public static void assertArchive(File expectedDirectory, File actualFile) throws IOException {
		File actualDirectory = new File(getTestDirectory(), "actual.tgz");
		FileUtils.deleteQuietly(actualDirectory);
		// explode actualFile to testdir
		FileUtils.copyFile(actualFile, actualDirectory);
		TarTests.explode(actualDirectory, true);
		// ensure only tar files in exploded testdir
		List<File> nonTars = Files.fileTreeTraverser().postOrderTraversal(actualDirectory).filter(file -> file.isFile() && !endsWith(file.getName(), ".tar")).toList();
		assertThat(nonTars).isEmpty();
		// explode all tar files
		List<File> tars = Files.fileTreeTraverser().postOrderTraversal(actualDirectory).filter(file -> file.isFile() && endsWith(file.getName(), ".tar")).toList();
		for (File tar: tars) {
			TarTests.explode(tar, false);
		}
		// compare directories
		List<String> compareExpected = determineElements(expectedDirectory);
		List<String> compareActual = determineElements(actualDirectory);
		assertThat(compareActual).containsOnlyElementsOf(compareExpected);
	}


	protected static List<String> determineElements(File root) throws IOException {
		List<String> result = new ArrayList<>();
		String rootPath = root.getAbsolutePath();
		List<File> files = Files.fileTreeTraverser().preOrderTraversal(root).filter(
			file -> file.isFile() && !StringUtils.equals(file.getName(), "git-empty-dir")).toList();
		for (File file: files) {
			String path = removeStart(file.getAbsolutePath(), rootPath);
			String entry = path;
			if (file.isFile()) {
				entry += ", " + Files.hash(file, Hashing.md5());
			}
			result.add(entry);
			if (file.getName().equals("meta.json")) {
				Logr.get().info("meta '{}':  {}", file.getAbsoluteFile(), FileUtils.readFileToString(file, Charsets.UTF_8));
			}
		}
		return result;
	}
}
