package de.galan.dmsexchange.meta;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.galan.dmsexchange.util.EmailValidation;


/**
 * A user an entity is associated with. Users are stored as email-address, the mapping has to be done by the executing
 * client/dms.
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
		if (!EmailValidation.isValidEmailAddress(getEmail())) {
			result.add("Invalid email for user '" + getEmail() + "'");
		}
	}


	@Override
	public int hashCode() {
		return Objects.hash(email);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			final User other = (User)obj;
			return new EqualsBuilder().append(email, other.email).isEquals();
		}
		return false;
	}

}
