package de.galan.dmsexchange.meta;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;


/**
 * Collects errors during the validation process
 */
public class ValidationResult {

	private List<String> errors;


	public void add(String error) {
		if (getErrors() == null) {
			errors = Lists.newArrayList();
		}
		getErrors().add(error);
	}


	public boolean hasErrors() {
		return getErrors() != null && !getErrors().isEmpty();
	}


	public List<String> getErrors() {
		return errors;
	}


	public String getErrorsJoined() {
		return Joiner.on(", ").skipNulls().join(getErrors());
	}

}
