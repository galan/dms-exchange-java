package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.dmsexchange.util.UtcFormatter;


/**
 * CUT Comment
 *
 * @author daniel
 */
public class CommentTest extends AbstractTestParent {

	@Test
	public void equality() throws Exception {
		Comment c1 = new Comment(new User("first@example.com"), UtcFormatter.parse("2014-12-28T20:00:15Z"), "Hello");
		Comment c2 = new Comment(new User("first@example.com"), UtcFormatter.parse("2014-12-28T20:00:15Z"), "Hello");
		Comment c3 = new Comment().commentBy("first@example.com").commentTime("2014-12-28T20:00:15Z").content("Hello");
		Comment c4 = new Comment().commentBy("first@example.com").commentTime(UtcFormatter.parse("2014-12-28T20:00:15Z")).content("Hello");
		Comment c5 = new Comment(new User("other@example.com"), UtcFormatter.parse("2014-12-28T20:00:15Z"), "Hello");
		assertThat(c1).isEqualTo(c2);
		assertThat(c1).isEqualTo(c3);
		assertThat(c1).isEqualTo(c4);
		assertThat(c1).isNotEqualTo(c5);
	}

}
