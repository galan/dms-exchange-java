package de.galan.dmsexchange.exchange.write;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;

import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.DocumentValidationException;
import de.galan.dmsexchange.exchange.container.ContainerSerializer;
import de.galan.dmsexchange.meta.document.Document;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.archive.TarUtil;


/**
 * Adds documents to a specified file. Using generated directories and document-container names inside the archive
 * during the process. It is artifical limited to one billion containers, otherwise the archive should be splitted using
 * the ConditionalDmsWriter.
 *
 * @author daniel
 */
public class DefaultDmsWriter extends DefaultExchange implements DmsWriter {

	private int counterContainer = 0;
	private ContainerSerializer serializer;
	private TarArchiveOutputStream tar;


	public DefaultDmsWriter(File file) throws DmsExchangeException, FileNotFoundException {
		this(new FileOutputStream(file));
	}


	public DefaultDmsWriter(OutputStream outputstream) throws DmsExchangeException {
		serializer = new ContainerSerializer();
		try {
			tar = TarUtil.create(outputstream, true);
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to create archive stream", ex);
		}
		registerListener(new DocumentAddedLoggingListener());
	}


	@Override
	public void add(Document document) throws DmsExchangeException {
		try {
			String pathForContainer = getNextContainerPath();
			byte[] container = serializer.archive(document, false);
			TarUtil.addEntry(tar, container, pathForContainer);
			postEvent(new DocumentAddedEvent(document));
		}
		catch (DocumentValidationException ex) {
			postEvent(new DocumentAddedFailedEvent(document, ex.getValidationResult()));
			throw ex;
		}
		catch (IOException ex) {
			postEvent(new DocumentAddedFailedEvent(document, null, ex));
			throw new DmsExchangeException("Failed adding", ex);
		}
	}


	/**
	 * Returns the name of the next container in the export-archive. It is limited to one billion containers, which
	 * should be enough. Large archives should be splitted, or own implementations should be used. Even if an average
	 * container is only 10k in size, this would result in a single ~1 TB export-archive.
	 *
	 * @throws DmsExchangeException
	 */
	protected String getNextContainerPath() throws DmsExchangeException {
		if (counterContainer > 1_000_000_000) {
			throw new DmsExchangeException("Limit for containers in single archive exceeded");
		}
		String string = leftPad("" + counterContainer++, 9, "0");
		StringBuffer result = new StringBuffer();
		result.append(StringUtils.substring(string, 0, 3));
		result.append("/");
		result.append(StringUtils.substring(string, 3, 6));
		result.append("/");
		result.append(StringUtils.substring(string, 6, 9));
		result.append(".tar");
		return result.toString();
	}


	/** Closes the zip file and writes the export-meta data */
	@Override
	public void close() throws DmsExchangeException {
		try {
			if (tar != null) {
				tar.close();
			}
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to close tar stream", ex);
		}
		finally {
			super.close();
		}
	}


	@Override
	public List<File> getFiles() {
		return ImmutableList.of(getFile());
	}

}
