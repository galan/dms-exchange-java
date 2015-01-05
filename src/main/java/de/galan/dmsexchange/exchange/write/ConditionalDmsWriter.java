package de.galan.dmsexchange.exchange.write;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.write.condition.SplitCondition;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.FileGenerationUtil;


/**
 * DmsWriter which creates multiple export-archives, depending on the given Conditions.
 *
 * @author daniel
 */
public class ConditionalDmsWriter implements DmsWriter {

	private DmsWriter writer;
	private File directory;
	private List<File> files;

	private List<SplitCondition> conditions;
	private int documentsAdded;


	public ConditionalDmsWriter(File directory) throws DmsExchangeException {
		this.directory = directory;
		conditions = new ArrayList<>();
		files = new ArrayList<>();
		documentsAdded = 0;
		setWriter(new DefaultDmsWriter(getNextFile()));
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


	protected File getCurrentFile() {
		return getFiles().get(getFiles().size() - 1);
	}


	@Override
	public void add(Document document) throws DmsExchangeException {
		if (conditions.stream().anyMatch(c -> c.evaluate(getCurrentFile(), documentsAdded))) {
			split();
		}
		writer.add(document);
		documentsAdded++;
	}


	protected void split() throws DmsExchangeException {
		try {
			getWriter().close();
		}
		catch (Exception ex) {
			throw new DmsExchangeException("Unable to close splitted export-archive", ex);
		}
		setWriter(new DefaultDmsWriter(getNextFile()));
		documentsAdded = 0;
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
