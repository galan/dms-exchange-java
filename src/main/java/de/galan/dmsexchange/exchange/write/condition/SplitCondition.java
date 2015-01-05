package de.galan.dmsexchange.exchange.write.condition;

import java.io.File;


/**
 * Evaluates if the export-archive has to be splitted.
 *
 * @author daniel
 */
public interface SplitCondition {

	public boolean evaluate(File file, int documentsAdded);

}
