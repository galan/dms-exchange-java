package de.galan.dmsexchange.exchange.write;

import java.io.File;
import java.util.List;

import de.galan.dmsexchange.exchange.DmsWriter;


/**
 * The conditionalDmsWriter is able to split the archive to multiple export-archives, depending on the given conditions.
 * The resulting archives are retrievable after the export using the getFiles() method.
 *
 * @author daniel
 */
public interface ConditionalDmsWriter extends DmsWriter {

	/** Returns the list of export-archives files the ConditionalDmsWriter has created during export. */
	public List<File> getFiles();

}
