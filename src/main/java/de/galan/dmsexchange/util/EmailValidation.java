package de.galan.dmsexchange.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * Validates email using javas native InternetAddress validation.
 *
 * @author daniel
 */
public class EmailValidation {

	public static boolean isValidEmailAddress(String emailAddress) {
		boolean result = true;
		try {
			new InternetAddress(emailAddress).validate();
		}
		catch (AddressException ex) {
			result = false;
		}
		return result;
	}

}
