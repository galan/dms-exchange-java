package de.galan.dmsexchange.exchange.write;

import static java.util.stream.Collectors.*;

import org.slf4j.Logger;

import com.google.common.eventbus.Subscribe;

import de.galan.commons.logging.Logr;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DocumentFailedListener {

	private static final Logger LOG = Logr.get();


	@Subscribe
	public void documentFailed(DocumentFailedEvent event) {
		if (event.getValidationResult() == null) {
			LOG.info("Document adding failed");
		}
		else {
			String joined = event.getValidationResult().getErrors().stream().collect(joining("", "- ", "\n"));
			LOG.info("Document validation failed:{}", joined);
		}
	}

}
