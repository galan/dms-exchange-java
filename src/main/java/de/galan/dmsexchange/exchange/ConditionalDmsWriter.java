package de.galan.dmsexchange.exchange;

import java.io.File;
import java.util.List;


/**
 * The conditionalDmsWriter is able to split the archive to multiple export-archives, depending on the given conditions.
 * The resulting archives are retrievable after the export and calling close() using the getFiles() method.
 *
 * @author daniel
 */
public interface ConditionalDmsWriter extends DmsWriter {

	/** Returns the list of export-archives files a ConditionalDmsWriter has created during export. */
	public List<File> getFiles();

}
