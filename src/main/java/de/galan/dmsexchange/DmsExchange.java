package de.galan.dmsexchange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.read.DefaultDmsReader;
import de.galan.dmsexchange.exchange.write.ConditionalDmsWriter;
import de.galan.dmsexchange.exchange.write.DefaultDmsWriter;
import de.galan.dmsexchange.exchange.write.condition.DocumentsSplitCondition;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.FileGenerationUtil;


/**
 * Simplified entry-point for DmsExchange use-cases.
 *
 * @author daniel
 */
public class DmsExchange {

	public static DmsReader createReader(String file) throws DmsExchangeException {
		return new DefaultDmsReader(new File(file));
	}


	public static DmsReader createReader(File file) throws DmsExchangeException {
		return new DefaultDmsReader(file);
	}


	public static DmsWriter createWriter(File fileOrDirectory) throws DmsExchangeException, FileNotFoundException {
		return new DefaultDmsWriter(FileGenerationUtil.determineFile(fileOrDirectory));
	}


	public static DmsWriter createWriter(OutputStream outputstream) throws DmsExchangeException {
		return new DefaultDmsWriter(outputstream);
	}


	public static DmsWriter createWriter(File directory, Integer thresholdDocuments) throws DmsExchangeException {
		ConditionalDmsWriter writer = new ConditionalDmsWriter(directory);
		if (thresholdDocuments != null) {
			writer.addCondition(new DocumentsSplitCondition(thresholdDocuments));
		}
		return writer;
	}

	/*
	public static DmsWriter createWriter(File directory, Integer thresholdDocuments, Integer thresholdFilesize) throws DmsExchangeException {
		ConditionalDmsWriter writer = new ConditionalDmsWriter(directory);
		if (thresholdDocuments != null) {
			writer.addCondition(new DocumentsSplitCondition(thresholdDocuments));
		}
		if (thresholdFilesize != null) {
			// Issues due to Nio ZipFileSystem
			writer.addCondition(new FilesizeSplitCondition(thresholdFilesize));
		}
		return writer;
	}
	 */

}
