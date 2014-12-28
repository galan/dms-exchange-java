package de.galan.dmsexchange.meta;

import java.util.List;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public interface Validatable {

	public void validate(ValidationResult result);


	default ValidationResult validate() {
		ValidationResult result = new ValidationResult();
		validate(result);
		return result;
	}


	default void validate(ValidationResult result, List<? extends Validatable> validatables) {
		if (validatables != null) {
			validatables.stream().forEach(v -> v.validate(result));
		}
	}

}
