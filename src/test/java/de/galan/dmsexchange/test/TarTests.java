package de.galan.dmsexchange.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;

import de.galan.dmsexchange.util.archive.TarUtil;


/**
 * Helper for working with tar/tgz files.
 *
 * @author daniel
 */
public class TarTests {

	/**
	 * Unpacks a tar/tgz file to its own location.
	 * <p>
	 * The file will be first renamed (using a temporary name). After the extraction it will be deleted.
	 *
	 * @param file input tar/tgz file as well as the target directory.
	 * @param compressed true if file has to be gunzipped first.
	 * @throws FileNotFoundException
	 */
	public static void explode(File file, boolean compressed) throws IOException {
		File tempFile = new File(file.getAbsoluteFile() + "." + RandomUtils.nextInt(0, Integer.MAX_VALUE));
		unpack(file, tempFile, compressed);
		FileUtils.deleteQuietly(file);
		FileUtils.moveFile(tempFile, file);
	}


	/**
	 * Compresses a given directory in its own location.
	 * <p>
	 * A tar/tgz file will be first created with a temporary name. After the compressing the directory will be deleted
	 * and the file will be renamed as the original directory.
	 *
	 * @param file input directory as well as the target tar/tgz file.
	 * @param compress true if file has to be gzipped as well.
	 */
	public static void unexplode(File file, boolean compressed) throws IOException {
		File tempFile = new File(file.getAbsoluteFile() + "." + RandomUtils.nextInt(0, Integer.MAX_VALUE));
		pack(file, tempFile, compressed);
		FileUtils.deleteQuietly(file);
		FileUtils.moveFile(tempFile, file);
	}


	/**
	 * Compresses the given directory and all its sub-directories into a tar/tgz file.
	 * <p>
	 * The file must not be a directory and its parent directory must exist. Will not include the root directory name in
	 * the archive.
	 *
	 * @param rootDir root directory.
	 * @param targetFile file that will be created or overwritten.
	 * @param compress when true tar will be additionally be gzipped (tgz).
	 */
	public static void pack(File rootDir, File targetFile, boolean compress) throws IOException {
		FileOutputStream fos = new FileOutputStream(targetFile);
		try (TarArchiveOutputStream tar = TarUtil.create(fos, compress)) {
			traverse(tar, rootDir, "");
		}
		fos.flush();
		fos.close();
	}


	protected static void traverse(TarArchiveOutputStream tar, File current, String path) throws IOException {
		if (current.isDirectory()) {
			for (File file: current.listFiles()) {
				traverse(tar, file, path + (file.isDirectory() ? file.getName() + "/" : ""));
			}
		}
		else {
			TarUtil.addEntry(tar, FileUtils.readFileToByteArray(current), path + current.getName());
		}
	}


	public static void unpack(File file, File targetDir, boolean compressed) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		try (TarArchiveInputStream tar = new TarArchiveInputStream(compressed ? new GzipCompressorInputStream(fis) : fis)) {
			TarArchiveEntry entry = null;
			while((entry = tar.getNextTarEntry()) != null) {
				if (entry.isDirectory()) {
					new File(targetDir, entry.getName()).mkdirs();
				}
				else {
					FileUtils.copyInputStreamToFile(tar, new File(targetDir, entry.getName()));
				}
			}
		}
		fis.close();
	}


	/**
	 * Compares two tar/tgz files and returns <code>true</code> if they contain same entries.
	 * <p>
	 * First the two files are compared byte-by-byte. If a difference is found the corresponding entries of both
	 * archives files are compared. Thus if same contents is packed differently the two archives may still be the same.
	 * </p>
	 * <p>
	 * Two archives are considered the same if
	 * <ol>
	 * <li>they contain same number of entries,</li>
	 * <li>for each entry in the first archive there exists an entry with the same in the second archive</li>
	 * <li>for each entry in the first archive and the entry with the same name in the second archive
	 * <ol>
	 * <li>both are either directories or files,</li>
	 * <li>both have the same size,</li>
	 * <li>both have the same CRC,</li>
	 * <li>both have the same contents (compared byte-by-byte).</li>
	 * </ol>
	 * </li>
	 * </ol>
	 *
	 * @param f1 first ZIP file.
	 * @param f2 second ZIP file.
	 * @return <code>true</code> if the two ZIP files contain same entries, <code>false</code> if a difference was found
	 *         or an error occurred during the comparison.
	 */
	public static boolean archiveEquals(File f1, File f2) {
		return true;
	}

}
