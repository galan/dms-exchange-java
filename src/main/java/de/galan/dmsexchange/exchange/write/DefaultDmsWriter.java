package de.galan.dmsexchange.exchange.write;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;

import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsWriter;
import de.galan.dmsexchange.exchange.DocumentValidationException;
import de.galan.dmsexchange.exchange.container.ContainerSerializer;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.dmsexchange.util.archive.TarUtil;


/**
 * Adds documents to a specified file. Using generated directories and document-container names inside the archive
 * during the process. It is artifical limited to one billion containers, otherwise the archive should be splitted using
 * the ConditionalDmsWriter.
 */
public class DefaultDmsWriter extends DefaultExchange implements DmsWriter {

	private static final String ZERO = "0";
	private static final String EXTENSION = ".tar";
	private static final String SLASH = "/";

	private long counterContainer = 0;
	private ContainerSerializer serializer;
	private TarArchiveOutputStream tar;
	private boolean closed = false;


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
		if (isClosed()) {
			throw new DmsExchangeException("The export-archive is already closed");
		}
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
	 * Returns the name of the next container in the export-archive. It is limited to a trillion containers, which
	 * should be enough. Large archives should be splitted, or own implementations should be used. Even if an average
	 * container is only 10k in size, this would result in a single export-archive with ~1 PB data.
	 *
	 * @throws DmsExchangeException
	 */
	protected String getNextContainerPath() throws DmsExchangeException {
		if (counterContainer - 1 > 1_0000_0000_0000L) {
			throw new DmsExchangeException("Limit for containers in single archive exceeded");
		}
		String string = leftPad(EMPTY + counterContainer++, 12, ZERO);
		StringBuffer result = new StringBuffer();
		result.append(StringUtils.substring(string, 0, 4));
		result.append(SLASH);
		result.append(StringUtils.substring(string, 4, 8));
		result.append(SLASH);
		result.append(StringUtils.substring(string, 8, 12));
		result.append(EXTENSION);
		return result.toString();
	}


	/** Closes the archive file and writes the export-meta data */
	@Override
	public void close() throws DmsExchangeException {
		if (!isClosed()) {
			try {
				if (tar != null) {
					tar.close();
				}
			}
			catch (IOException ex) {
				throw new DmsExchangeException("Unable to close tar stream", ex);
			}
			closed = true;
		}
	}


	protected boolean isClosed() {
		return closed;
	}

}
