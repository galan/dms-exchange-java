package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import com.google.common.io.Resources;

import de.galan.dmsexchange.util.UtcFormatter;


/**
 * CUT Revision
 */
public class RevisionTest {

	@Test
	public void equality() throws Exception {
		Revision r1 = new Revision(UtcFormatter.parse("2014-12-28T20:00:15Z"), new User("sample@example.com"), new byte[] {65, 66, 67});
		Revision r2 = new Revision(UtcFormatter.parse("2014-12-28T20:00:15Z"), new User("sample@example.com"), new byte[] {65, 66, 67});
		Revision r3 = new Revision().addedTime(UtcFormatter.parse("2014-12-28T20:00:15Z")).addedBy(new User("sample@example.com"))
			.data(new byte[] {65, 66, 67});
		Revision r4 = new Revision().addedTime("2014-12-28T20:00:15Z").addedBy("sample@example.com").data(new byte[] {65, 66, 67});
		URL url = Resources.getResource(getClass(), getClass().getSimpleName() + "-data");
		Revision r5 = new Revision().addedTime("2014-12-28T20:00:15Z").addedBy("sample@example.com").data(new File(url.getFile()));
		Revision r6 = new Revision().addedTime("2014-12-28T20:00:15Z").addedBy("sample@example.com").data(url.openStream());
		Revision r7 = new Revision().addedTime("2014-12-28T20:00:15Z").addedBy("other@example.com").data(new byte[] {65, 66, 67});
		assertThat(r1).isEqualTo(r2);
		assertThat(r1).isEqualTo(r3);
		assertThat(r1).isEqualTo(r4);
		assertThat(r1).isEqualTo(r5);
		assertThat(r1).isEqualTo(r6);
		assertThat(r1).isNotEqualTo(r7);
	}


	@Test
	public void validateOk() throws Exception {
		Revision r1 = new Revision().addedTime("2014-12-28T20:00:15Z").addedBy("other@example.com").data(new byte[] {65, 66, 67}); // all fields
		assertThat(r1.validate().hasErrors()).isFalse();
		Revision r2 = new Revision().addedTime("2014-12-28T20:00:15Z").data(new byte[] {65, 66, 67}); // no addedBy
		assertThat(r2.validate().hasErrors()).isFalse();
	}


	@Test
	public void validateInvalidEmail() throws Exception {
		Revision r1 = new Revision().addedTime("2014-12-28T20:00:15Z").addedBy("other-example.com").data(new byte[] {65, 66, 67});
		assertThat(r1.validate().hasErrors()).isTrue();
		assertThat(r1.validate().getErrors().get(0)).isEqualTo("Invalid email for user 'other-example.com'");
		assertThat(r1.validate().getErrorsJoined()).isEqualTo("Invalid email for user 'other-example.com'");
	}


	@Test
	public void validateInvalidAddedTime() throws Exception {
		Revision r1 = new Revision().data(new byte[] {65, 66, 67});
		assertThat(r1.validate().hasErrors()).isTrue();
		assertThat(r1.validate().getErrors().get(0)).isEqualTo("No addedTime for revision");
		assertThat(r1.validate().getErrorsJoined()).isEqualTo("No addedTime for revision");
	}


	@Test
	public void validateInvalidData() throws Exception {
		Revision r1 = new Revision().addedTime("2014-12-28T20:00:15Z");
		assertThat(r1.validate().hasErrors()).isTrue();
		assertThat(r1.validate().getErrors().get(0)).isEqualTo("No data for revision");
		assertThat(r1.validate().getErrorsJoined()).isEqualTo("No data for revision");
	}

}
