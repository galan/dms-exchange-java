package de.galan.dmsexchange.meta;

import java.util.List;


/**
 * Entities that could be validated prior adding to an export-archive have to implement this contract.
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


	default void validate(ValidationResult result, Validatable validatable) {
		if (validatable != null) {
			validatable.validate(result);
		}
	}

}
