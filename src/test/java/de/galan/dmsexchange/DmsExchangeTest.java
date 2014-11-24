package de.galan.dmsexchange;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DmsExchangeTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		DmsExchange exchange = new DmsExchange("/home/daniel/temp/dummy.zip");
		exchange.exchange.close();
	}

}
