package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.dmsexchange.util.UtcFormatter;


/**
 * CUT Context
 *
 * @author daniel
 */
public class ContextTest {

	@Test
	public void equality() throws Exception {
		Context c1 = new Context(UtcFormatter.parse("2014-12-28T20:00:15Z"), UtcFormatter.parse("2015-05-17T19:00:11Z"));
		Context c2 = new Context(UtcFormatter.parse("2014-12-28T20:00:15Z"), UtcFormatter.parse("2015-05-17T19:00:11Z"));
		Context c3 = new Context().documentTime(UtcFormatter.parse("2014-12-28T20:00:15Z")).dueDateTime(UtcFormatter.parse("2015-05-17T19:00:11Z"));
		Context c4 = new Context().documentTime("2014-12-28T20:00:15Z").dueDateTime("2015-05-17T19:00:11Z");
		Context c5 = new Context(UtcFormatter.parse("2014-12-28T20:00:20Z"), UtcFormatter.parse("2015-05-17T19:00:11Z"));
		assertThat(c1).isEqualTo(c2);
		assertThat(c1).isEqualTo(c3);
		assertThat(c1).isEqualTo(c4);
		assertThat(c1).isNotEqualTo(c5);
	}

}
