package de.galan.dmsexchange.exchange.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.Consumer;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.dmsexchange.exchange.DefaultExchange;
import de.galan.dmsexchange.exchange.DmsReader;
import de.galan.dmsexchange.exchange.container.ContainerDeserializer;
import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.util.DmsExchangeException;
import de.galan.verjson.util.ReadException;


/**
 * Reads all document-containers inside the given export-archive. The read document-containers will be posted to the
 * registered listeners.
 *
 * @author daniel
 */
public class DefaultDmsReader extends DefaultExchange implements DmsReader {

	private static final Logger LOG = Logr.get();
	private InputStream inputstream;
	private ContainerDeserializer deserializer;


	public DefaultDmsReader(InputStream inputstream) {
		this.inputstream = inputstream;
		deserializer = new ContainerDeserializer();
	}


	@Override
	public void registerListener(Object... listeners) {
		Arrays.asList(listeners).stream().forEach(this::registerListener);
	}


	@Override
	public void readDocuments(Consumer<Document> consumer) throws DmsExchangeException {
		if (consumer == null) {
			throw new NullPointerException("Consumer is null");
		}
		WrappingDocumentConsumer wrapper = new WrappingDocumentConsumer(consumer);
		registerListener(wrapper);
		try {
			readDocuments();
		}
		finally {
			unregisterListener(wrapper);
		}
	}


	@Override
	public void readDocuments() throws DmsExchangeException {
		// iterate over directories recursivly, this blocks until finished
		CountingDocumentConsumer counter = new CountingDocumentConsumer();
		registerListener(counter);
		try {
			readArchive();
		}
		finally {
			unregisterListener(counter);
		}
		LOG.info("Finished reading export-archive with {} documents", counter.getCountedDocuments());
	}


	private void readArchive() throws DmsExchangeException {
		try (TarArchiveInputStream tar = new TarArchiveInputStream(new GzipCompressorInputStream(inputstream))) {
			TarArchiveEntry entry = null;
			while((entry = tar.getNextTarEntry()) != null) {
				if (!entry.isDirectory()) {
					if (entry.isFile() && entry.getName().endsWith(".tar")) {
						try {
							Document document = deserializer.unarchive(tar, false);
							postEvent(document);
						}
						catch (DmsExchangeException ex) {
							postEvent(new DocumentReadInvalidEvent(entry.getName()));
						}
						catch (ReadException ex) {
							postEvent(new DocumentReadInvalidEvent(entry.getName()));
						}
					}
					else {
						LOG.warn("Unrecognized element: {}", entry.getName());
					}
				}
			}
		}
		catch (IOException ex) {
			throw new DmsExchangeException("Unable to read container tar", ex);
		}
	}

}
