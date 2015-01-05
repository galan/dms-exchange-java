package de.galan.dmsexchange.exchange.write;

import java.io.File;
import java.util.List;

import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.FileGenerationUtil;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class ConditionalDmsWriter implements DmsWriter {

	private DmsWriter writer;
	private File directory;
	private List<File> files;


	public ConditionalDmsWriter(File directory) throws DmsExchangeException {
		this.directory = directory;
		setWriter(new DefaultDmsWriter(getNextFile()));
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


	@Override
	public void add(Document document) throws DmsExchangeException {
		if (getWriter() == null) {
			//writer = new DefaultDmsWriter(file);
		}
		writer.add(document);
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
