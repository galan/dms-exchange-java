package de.galan.dmsexchange.util.zip;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.Comparator;


/**
 * Sorts pathes by filename, files (without ending slash) first.
 *
 * @author daniel
 */
public class FilelistComparator implements Comparator<String> {

	private static final String SLASH = "/";


	@Override
	public int compare(String path1, String path2) {
		boolean path1Dir = endsWith(path1, SLASH);
		boolean path2Dir = endsWith(path2, SLASH);
		if (!(path1Dir ^ path2Dir)) {
			return path1.compareTo(path2);
		}
		if (path1Dir) {
			return 1;
		}
		return -1;
	}

}
