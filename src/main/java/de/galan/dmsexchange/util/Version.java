package de.galan.dmsexchange.util;

import java.util.HashMap;
import java.util.Map;


/**
 * Mapping of dxs versions against verjson. This is done manually since there is no version-wrapper which verjson is
 * typically using.
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
		Long result = null;
		if (dmsVersion != null) {
			result = versionToVerjson.get(dmsVersion);
		}
		return result;
	}

}
