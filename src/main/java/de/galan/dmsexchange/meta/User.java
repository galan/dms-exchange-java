package de.galan.dmsexchange.meta;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class User implements Validatable {

	private String email;


	public User(String email) {
		this.email = email;
	}


	public String getEmail() {
		return email;
	}


	@Override
	public String toString() {
		return getEmail();
	}


	@Override
	public void validate(ValidationResult result) {
		if (!isValidEmailAddress(getEmail())) {
			result.add("Invalid email for user '" + getEmail() + "'");
		}
	}


	protected boolean isValidEmailAddress(String emailAddress) {
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
