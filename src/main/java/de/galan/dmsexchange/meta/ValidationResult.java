package de.galan.dmsexchange.meta;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
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
		return errors == null ? Collections.emptyList() : errors;
	}

}
