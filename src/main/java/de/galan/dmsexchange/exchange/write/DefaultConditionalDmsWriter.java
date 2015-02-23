package de.galan.dmsexchange.exchange.write;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.write.condition.SplitCondition;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.FileGenerationUtil;


/**
 * DmsWriter which creates multiple export-archives, depending on the given Conditions.
 *
 * @author daniel
 */
public class DefaultConditionalDmsWriter implements ConditionalDmsWriter {

	private DmsWriter writer;
	private File directory;
	private List<File> files;

	private List<SplitCondition> conditions;
	private int documentsAdded;
	private FileOutputStream currentFileOutputStream;


	public DefaultConditionalDmsWriter(File directory) throws DmsExchangeException {
		if (directory == null || !directory.isDirectory()) {
			throw new DmsExchangeException("File passed to ConditionalDmsWriter must be an existing directory");
		}
		this.directory = directory;
		conditions = new ArrayList<>();
		files = new ArrayList<>();
		documentsAdded = 0;
		setWriter(getNextWriter());
	}


	public void addCondition(SplitCondition condition) {
		conditions.add(condition);
	}


	protected DmsWriter getWriter() {
		return writer;
	}


	protected void setWriter(DmsWriter writer) {
		this.writer = writer;
	}


	protected File getCurrentFile() {
		return getFiles().get(getFiles().size() - 1);
	}


	@Override
	public void add(Document document) throws DmsExchangeException {
		if (conditions.stream().anyMatch(c -> c.evaluate(getCurrentFile(), documentsAdded))) {
			split();
		}
		writer.add(document);
		try {
			currentFileOutputStream.flush();
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to flush data to file", ex);
		}
		documentsAdded++;
	}


	protected void split() throws DmsExchangeException {
		try {
			getWriter().close();
		}
		catch (Exception ex) {
			throw new DmsExchangeException("Unable to close splitted export-archive", ex);
		}
		setWriter(getNextWriter());
		documentsAdded = 0;
	}


	protected DmsWriter getNextWriter() throws DmsExchangeException {
		File file = getNextFile();
		try {
			currentFileOutputStream = new FileOutputStream(file);
			return new DefaultDmsWriter(currentFileOutputStream);
		}
		catch (FileNotFoundException ex) {
			throw new DmsExchangeException("Unable to create file '" + file.getAbsolutePath() + "'", ex);
		}
	}


	protected File getNextFile() throws DmsExchangeException {
		try {
			File result = FileGenerationUtil.generateUniqueFilename(directory);
			files.add(result);
			return result;
		}
		catch (Exception ex) {
			throw new DmsExchangeException("Unable to create unique filename in directory", ex);
		}
	}


	@Override
	public void close() throws Exception {
		if (getWriter() != null) {
			getWriter().close();
		}
	}


	@Override
	public List<File> getFiles() {
		return files;
	}

}
