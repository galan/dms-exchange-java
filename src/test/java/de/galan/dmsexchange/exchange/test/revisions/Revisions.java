package de.galan.dmsexchange.exchange.test.revisions;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.IOException;
import java.time.ZonedDateTime;

import de.galan.dmsexchange.meta.Revision;
import de.galan.dmsexchange.meta.User;


/**
 * Helper for revisions
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
