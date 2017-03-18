package de.galan.dmsexchange.meta;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.galan.dmsexchange.util.EmailValidation;


/**
 * Information about the exporting dms.
 */
public class Source implements Validatable {

	private String name;
	private String version;
	private String url;
	private String email;


	public Source() {
		// empty, default constructor required for Jackson
	}


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
	public int hashCode() {
		return Objects.hash(name, version, url, email);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Source) {
			final Source other = (Source)obj;
			return new EqualsBuilder().append(name, other.name).append(version, other.version).append(url, other.url).append(email, other.email).isEquals();
		}
		return false;
	}


	@Override
	public void validate(ValidationResult result) {
		if (getUrl() != null && !isValidUrl(getUrl())) {
			result.add("Invalid URL for source URL");
		}
		if (getEmail() != null && !EmailValidation.isValidEmailAddress(getEmail())) {
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

}
