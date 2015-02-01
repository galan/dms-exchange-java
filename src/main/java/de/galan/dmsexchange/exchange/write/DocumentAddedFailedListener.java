package de.galan.dmsexchange.exchange.write;

import org.slf4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.eventbus.Subscribe;

import de.galan.commons.logging.Logr;
import de.galan.dmsexchange.meta.export.Export;


/**
 * Adds the errormessages from the invalid documents in the export.
 *
 * @author daniel
 */
public class DocumentAddedFailedListener {

	private static final Logger LOG = Logr.get();

	private Export export;


	public DocumentAddedFailedListener(Export export) {
		this.export = export;
	}


	@Subscribe
	public void documentFailed(DocumentAddedFailedEvent event) {
		if (event.getValidationResult() == null) {
			LOG.info("Document adding failed");
			export.addDocumentFailed("Document adding failed", event.getDocument().getIdSystem(), event.getDocument().getIdUser());
		}
		else {
			String joined = Joiner.on(", ").skipNulls().join(event.getValidationResult().getErrors());
			LOG.info("Document validation failed: {}", joined);
			export.addDocumentFailed(joined, event.getDocument().getIdSystem(), event.getDocument().getIdUser());
		}
	}

}
