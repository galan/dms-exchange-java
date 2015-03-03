package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import com.google.common.io.Resources;

import de.galan.dmsexchange.util.UtcFormatter;


/**
 * CUT Revision
 *
 * @author daniel
 */
public class RevisionTest {

	@Test
	public void equality() throws Exception {
		Revision r1 = new Revision(UtcFormatter.parse("2014-12-28T20:00:15Z"), new User("sample@example.com"), new byte[] {65, 66, 67});
		Revision r2 = new Revision(UtcFormatter.parse("2014-12-28T20:00:15Z"), new User("sample@example.com"), new byte[] {65, 66, 67});
		Revision r3 = new Revision().addedTime(UtcFormatter.parse("2014-12-28T20:00:15Z")).addedBy(new User("sample@example.com")).data(new byte[] {65, 66, 67});
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

}
