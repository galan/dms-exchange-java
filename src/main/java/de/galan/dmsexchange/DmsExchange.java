package de.galan.dmsexchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import de.galan.dmsexchange.exchange.ConditionalDmsWriter;
import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.read.DefaultDmsReader;
import de.galan.dmsexchange.exchange.write.DefaultConditionalDmsWriter;
import de.galan.dmsexchange.exchange.write.DefaultDmsWriter;
import de.galan.dmsexchange.exchange.write.condition.DocumentsSplitCondition;
import de.galan.dmsexchange.exchange.write.condition.FilesizeSplitCondition;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.FileGenerationUtil;


/**
 * Entry-point for DmsExchange use-cases.
 *
 * @author daniel
 */
public class DmsExchange {

	/**
	 * Provides a export-archive reader for {@link Document}s that are contained in the passed file.
	 *
	 * @param file Absolute path to an export-archive with {@link Document}s
	 * @return A non thread-safe {@link DmsReader} that is used to read documents from a file.
	 * @throws DmsExchangeException If the file can't be read or other bad things happen
	 */
	public static DmsReader createReader(String file) throws DmsExchangeException {
		return createReader(new File(file));
	}


	/**
	 * Provides a export-archive reader for {@link Document}s that are contained in the passed file.
	 *
	 * @param file Location of the export-archive with {@link Document}s
	 * @return A non thread-safe {@link DmsReader} that is used to read documents from a file.
	 * @throws DmsExchangeException If the file can't be read or other bad things happen
	 */
	public static DmsReader createReader(File file) throws DmsExchangeException {
		return createReader(createFileInputStream(file));
	}


	/** Creates a FileInputStream from a file. */
	private static InputStream createFileInputStream(File file) throws DmsExchangeException {
		try {
			return new FileInputStream(file);
		}
		catch (FileNotFoundException ex) {
			throw new DmsExchangeException("Unable to open file", ex);
		}
	}


	/**
	 * Provides a export-archive reader for {@link Document}s that are read from the passed {@link InputStream}.
	 *
	 * @param inputstream Stream containing data from an export-archive
	 * @return A non thread-safe {@link DmsReader} that is used to read documents from the given InputStream.
	 * @throws DmsExchangeException If the data can't be read or other bad things happen
	 */
	public static DmsReader createReader(InputStream inputstream) {
		return new DefaultDmsReader(inputstream);
	}


	/**
	 * Provides a writer to create an export-archive with {@link Document}s that is written to the given {@link File}.
	 *
	 * @param file The file which is used to store the export-archive data. If the file already exists, it will be
	 *        overwritten. Non-existing parent-directories will be created. If file is a directory, an
	 *        {@link IllegalArgumentException} will be thrown.
	 * @return A DmsWriter that accepts {@link Document}s and must be closed when finished.
	 * @throws DmsExchangeException Encapsulating parent Exception for erroneous behaviour
	 */
	public static DmsWriter createWriter(File file) throws DmsExchangeException {
		File preparedFile = FileGenerationUtil.prepareFile(file);
		try {
			FileOutputStream fos = new FileOutputStream(preparedFile);
			return new DefaultDmsWriter(fos);
		}
		catch (FileNotFoundException ex) {
			throw new DmsExchangeException("Unable to create file '" + preparedFile.getAbsolutePath() + "'", ex);
		}
	}


	/**
	 * Provides a writer to create an export-archive with {@link Document}s that is written to the given
	 * {@link OutputStream}.
	 *
	 * @param outputstream OutputStream the export-archive is written to.
	 * @return A DmsWriter that accepts {@link Document}s and must be closed when finished.
	 * @throws DmsExchangeException Encapsulating parent Exception for erroneous behaviour
	 */
	public static DmsWriter createWriter(OutputStream outputstream) throws DmsExchangeException {
		return new DefaultDmsWriter(outputstream);
	}


	/**
	 * Provides a conditional writer to create (potentially) multiple export-archives with {@link Document}s in the
	 * given directory. If the given condition (thesholdDocuments) is matched, the export continuous on a new
	 * export-archive.
	 *
	 * @param directory Location where to create the export-archive files with a generated unique name.
	 * @param thresholdDocuments Limit of {@link Document}s a single export-archive can have, before creating a new
	 *        export-archive.
	 * @return A ConditionalDmsWriter that accepts {@link Document}s and must be closed when finished. The files written
	 *         by the instance can be queried using the getFiles() method.
	 * @throws DmsExchangeException Encapsulating parent Exception for erroneous behaviour
	 */
	public static ConditionalDmsWriter createWriter(File directory, Integer thresholdDocuments) throws DmsExchangeException {
		return createWriter(directory, thresholdDocuments, null);
	}


	/**
	 * Provides a conditional writer to create (potentially) multiple export-archives with {@link Document}s in the
	 * given directory. If one of the the given conditions (thesholdDocuments or thresholdFilesize) is matched, the
	 * export continuous on a new export-archive.
	 *
	 * @param directory Location where to create the export-archive files with a generated unique name.
	 * @param thresholdDocuments Limit of {@link Document}s a single export-archive can have, before creating a new
	 *        export-archive.
	 * @param thresholdFilesize Filesize in bytes the final export (approximately) can become, before creating a new
	 *        export-archive.
	 * @return A ConditionalDmsWriter that accepts {@link Document}s and must be closed when finished. The files written
	 *         by the instance can be queried using the getFiles() method.
	 * @throws DmsExchangeException Encapsulating parent Exception for erroneous behaviour
	 */
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
