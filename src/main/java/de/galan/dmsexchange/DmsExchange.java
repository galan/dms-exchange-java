package de.galan.dmsexchange;

import java.io.File;

import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.read.DefaultDmsReader;
import de.galan.dmsexchange.exchange.write.ConditionalDmsWriter;
import de.galan.dmsexchange.exchange.write.DefaultDmsWriter;
import de.galan.dmsexchange.exchange.write.condition.DocumentsSplitCondition;
import de.galan.dmsexchange.exchange.write.condition.FilesizeSplitCondition;
import de.galan.dmsexchange.util.DmsExchangeException;


/**
 * Simplified entry-point for DmsExchange use-cases.
 *
 * @author daniel
 */
public class DmsExchange {

	public static DmsReader createReader(File file) throws DmsExchangeException {
		return new DefaultDmsReader(file);
	}


	public static DmsWriter createWriter(File fileOrDirectory) throws DmsExchangeException {
		return new DefaultDmsWriter(fileOrDirectory);
	}


	public static DmsWriter createWriter(File directory, Integer thresholdDocuments, Integer thresholdFilesize) throws DmsExchangeException {
		ConditionalDmsWriter writer = new ConditionalDmsWriter(directory);
		if (thresholdDocuments != null) {
			writer.addCondition(new DocumentsSplitCondition(thresholdDocuments));
		}
		if (thresholdFilesize != null) {
			writer.addCondition(new FilesizeSplitCondition(thresholdFilesize));
		}
		return writer;
	}

}
