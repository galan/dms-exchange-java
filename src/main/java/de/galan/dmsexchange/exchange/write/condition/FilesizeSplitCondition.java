package de.galan.dmsexchange.exchange.write.condition;

import java.io.File;


/**
 * Evaluates to true if filesize exceeds the given threshold.
 */
public class FilesizeSplitCondition implements SplitCondition {

	private int thresholdFilesize;


	public FilesizeSplitCondition(int thresholdFilesize) {
		this.thresholdFilesize = thresholdFilesize;
	}


	@Override
	public boolean evaluate(File file, int documentsAdded) {
		return file.exists() && file.isFile() && file.length() >= thresholdFilesize;
	}

}
