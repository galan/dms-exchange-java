package de.galan.dmsexchange.meta;

import java.net.MalformedURLException;
import java.net.URL;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * Information about the exporting dms.
 *
 * @author daniel
 */
public class Source implements Validatable {

	private String name;
	private String version;
	private String url;
	private String email;


	public Source(String name, String version, String url, String email) {
		this.name = name;
		this.version = version;
		this.url = url;
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public String getVersion() {
		return version;
	}


	public String getUrl() {
		return url;
	}


	public String getEmail() {
		return email;
	}


	@Override
	public String toString() {
		return "Source [name=" + name + ", version=" + version + ", url=" + url + ", email=" + email + "]";
	}


	@Override
	public void validate(ValidationResult result) {
		if (getUrl() != null && !isValidUrl(getUrl())) {
			result.add("Invalid URL for source URL");
		}
		if (getEmail() != null && !isValidEmailAddress(getEmail())) {
			result.add("Invalid email-address for source email");
		}
	}


	private boolean isValidUrl(String validateUrl) {
		boolean result = true;
		try {
			new URL(validateUrl);
		}
		catch (MalformedURLException ex) {
			result = false;
		}
		return result;
	}


	private boolean isValidEmailAddress(String validateEmail) {
		boolean result = true;
		try {
			new InternetAddress(validateEmail).validate();
		}
		catch (AddressException ex) {
			result = false;
		}
		return result;
	}

}
