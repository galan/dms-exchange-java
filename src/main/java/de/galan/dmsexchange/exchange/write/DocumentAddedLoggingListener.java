package de.galan.dmsexchange.exchange.write;

import static org.apache.commons.lang3.StringUtils.*;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;

import de.galan.commons.logging.Logr;


/**
 * Logs if a document has been added, if available the systemId or userId will be appended.
 */
public class DocumentAddedLoggingListener {

	private static final Logger LOG = Logr.get();


	@Subscribe
	public void documentAdded(DocumentAddedEvent event) {
		boolean postfix = false;
		StringBuffer line = new StringBuffer("Added document");
		if (isNotBlank(event.getDocument().getIdSystem())) {
			line.append(" (idSystem:");
			line.append(event.getDocument().getIdSystem());
			postfix = true;
		}
		if (isNotBlank(event.getDocument().getIdUser())) {
			line.append(postfix ? ", idUser:" : " (idUser:");
			line.append(event.getDocument().getIdUser());
			postfix = true;
		}
		if (postfix) {
			line.append(")");
		}
		LOG.info(line.toString());
	}

}
