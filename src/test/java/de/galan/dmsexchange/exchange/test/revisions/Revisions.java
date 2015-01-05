package de.galan.dmsexchange.exchange.test.revisions;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.IOException;
import java.time.ZonedDateTime;

import de.galan.dmsexchange.meta.User;
import de.galan.dmsexchange.meta.document.Revision;


/**
 * Helper for revisions
 *
 * @author daniel
 */
public class Revisions {

	public static Revision read(String file, String tsAdded, String addedBy) throws IOException {
		ZonedDateTime zdt = ZonedDateTime.parse(tsAdded);
		Revision result = new Revision(zdt);
		if (isNotBlank(addedBy)) {
			result.setAddedBy(new User(addedBy));
		}
		result.setData(Revisions.class.getResourceAsStream(file));
		return result;
	}

}
