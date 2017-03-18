package de.galan.dmsexchange.util;

import java.util.HashMap;
import java.util.Map;


/**
 * Mapping of dms-exchange-specification versions against verjson. This is done manually since there is no
 * version-wrapper which verjson is typically using.
 */
public class Version {

	/** Latest version this library supports up to. */
	public static final String SUPPORTED_VERSION = "1.0.0";

	private static Map<String, Long> versionToVerjson;

	static {
		versionToVerjson = new HashMap<>();
		versionToVerjson.put("1.0.0", 1L);
	}


	public static Long getVerjson(String metaVersion) {
		Long result = null;
		if (metaVersion != null) {
			result = versionToVerjson.get(metaVersion);
		}
		return result;
	}

}
