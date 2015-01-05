package de.galan.dmsexchange.exchange.write.condition;

import java.io.File;


/**
 * Evaluates to true if documents added exceed the given threshold.
 *
 * @author daniel
 */
public class DocumentsSplitCondition implements SplitCondition {

	private int thresholdDocumentsAdded;


	public DocumentsSplitCondition(int thresholdDocumentsAdded) {
		this.thresholdDocumentsAdded = thresholdDocumentsAdded;
	}


	@Override
	public boolean evaluate(File file, int documentsAdded) {
		return documentsAdded >= thresholdDocumentsAdded;
	}

}
