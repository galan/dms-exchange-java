package de.galan.dmsexchange.meta;

import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.*;

import java.time.ZonedDateTime;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.dmsexchange.meta.Commenimport de.galan.dmsexchange.meta.document.Comment;
t;
import de.galan.dmsexchange.meta.User;


/**
 * CUT Comment
 *
 * @author daniel
 */
public class CommentTest extends AbstractTestParent {

	@Test
	public void equality() throws Exception {
		Comment c1 = new Comment(new User("first@example.com"), zdt("2014-12-28T20:00:15Z"), "Hello");
		Comment c2 = new Comment(new User("first@example.com"), zdt("2014-12-28T20:00:15Z"), "Hello");
		Comment c3 = new Comment(new User("other@example.com"), zdt("2014-12-28T20:00:15Z"), "Hello");
		assertThat(c1).isEqualTo(c2);
		assertThat(c1).isNotEqualTo(c3);
	}


	protected ZonedDateTime zdt(String utc) {
		return from(instantUtc(utc)).toZdt();
	}

}
