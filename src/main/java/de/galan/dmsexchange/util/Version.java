package de.galan.dmsexchange.util;

import java.util.HashMap;
import java.util.Map;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class Version {

	/** Latest version this library supports up to. */
	public static final String SUPPORTED_VERSION = "1.0.0-beta.3";

	private static Map<String, Long> versionToVerjson;

	static {
		versionToVerjson = new HashMap<>();
		versionToVerjson.put("1.0.0-beta.3", 1L);
	}


	public static Long getVerjson(String dmsVersion) {
		return versionToVerjson.get(dmsVersion);
	}

}
