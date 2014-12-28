package de.galan.dmsexchange.exchange.write;

import org.slf4j.Logger;

import com.google.common.eventbus.Subscribe;

import de.galan.commons.logging.Logr;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DocumentAddedListener {

	private static final Logger LOG = Logr.get();


	@Subscribe
	public void documentAdded(DocumentAddedEvent event) {
		LOG.info("Added document");
	}

}
