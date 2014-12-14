package de.galan.dmsexchange;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DmsExchangePrototypeTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		DmsExchangePrototype exchange = new DmsExchangePrototype("/home/daniel/temp/dummy.zip");
		exchange.close();
	}

}
