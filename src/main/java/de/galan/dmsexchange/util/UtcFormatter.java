package de.galan.dmsexchange.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import de.galan.commons.time.Instants;


/**
 * Global UTC DateTime-format.
 *
 * @author daniel
 */
public class UtcFormatter {

	public static final DateTimeFormatter UTC = DateTimeFormatter.ofPattern(Instants.DATE_FORMAT_UTC).withZone(Instants.ZONE_UTC);


	public static ZonedDateTime parse(String text) {
		return UTC.parse(text, ZonedDateTime::from);
	}

}
