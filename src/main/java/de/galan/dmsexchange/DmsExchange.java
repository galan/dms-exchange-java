package de.galan.dmsexchange;

import com.google.common.eventbus.EventBus;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DmsExchange {

	EventBus events;


	public DmsExchange() {
		events = new EventBus("dms-exchange");
	}


	public void registerListener(Object object) {
		events.register(object);
	}

}
