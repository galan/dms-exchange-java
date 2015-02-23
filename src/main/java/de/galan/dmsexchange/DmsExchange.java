package de.galan.dmsexchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.read.DefaultDmsReader;
import de.galan.dmsexchange.exchange.write.ConditionalDmsWriter;
import de.galan.dmsexchange.exchange.write.DefaultConditionalDmsWriter;
import de.galan.dmsexchange.exchange.write.DefaultDmsWriter;
import de.galan.dmsexchange.exchange.write.condition.DocumentsSplitCondition;
import de.galan.dmsexchange.exchange.write.condition.FilesizeSplitCondition;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.FileGenerationUtil;


/**
 * Simplified entry-point for DmsExchange use-cases.
 *
 * @author daniel
 */
public class DmsExchange {

	public static DmsReader createReader(String file) throws DmsExchangeException {
		return createReader(new File(file));
	}


	public static DmsReader createReader(File file) throws DmsExchangeException {
		return createReader(createFileInputStream(file));
	}


	private static InputStream createFileInputStream(File file) throws DmsExchangeException {
		try {
			return new FileInputStream(file);
		}
		catch (FileNotFoundException ex) {
			throw new DmsExchangeException("Unable to open file", ex);
		}
	}


	public static DmsReader createReader(InputStream inputstream) {
		return new DefaultDmsReader(inputstream);
	}


	public static DmsWriter createWriter(File fileOrDirectory) throws DmsExchangeException {
		File file = FileGenerationUtil.determineFile(fileOrDirectory);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			return new DefaultDmsWriter(fos);
		}
		catch (FileNotFoundException ex) {
			throw new DmsExchangeException("Unable to create file '" + file.getAbsolutePath() + "'", ex);
		}
	}


	public static DmsWriter createWriter(OutputStream outputstream) throws DmsExchangeException {
		return new DefaultDmsWriter(outputstream);
	}


	public static ConditionalDmsWriter createWriter(File directory, Integer thresholdDocuments) throws DmsExchangeException {
		return createWriter(directory, thresholdDocuments, null);
	}


	public static ConditionalDmsWriter createWriter(File directory, Integer thresholdDocuments, Integer thresholdFilesize) throws DmsExchangeException {
		DefaultConditionalDmsWriter writer = new DefaultConditionalDmsWriter(directory);
		if (thresholdDocuments != null) {
			writer.addCondition(new DocumentsSplitCondition(thresholdDocuments));
		}
		if (thresholdFilesize != null) {
			writer.addCondition(new FilesizeSplitCondition(thresholdFilesize));
		}
		return writer;
	}

}
