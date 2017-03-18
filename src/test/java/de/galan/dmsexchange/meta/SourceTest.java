package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT Source
 */
public class SourceTest extends AbstractTestParent {

	@Test
	public void equality() throws Exception {
		Source s1 = new Source("name", "version", "http://www.example.com", "email@example.com");
		Source s2 = new Source("name", "version", "http://www.example.com", "email@example.com");
		Source s3 = new Source("name2", "version", "http://www.example.com", "email@example.com");
		assertThat(s1).isEqualTo(s2);
		assertThat(s2).isNotEqualTo(s3);
	}


	@Test
	public void validateOk() throws Exception {
		Source s1 = new Source("name", "version", "http://www.example.com", "email@example.com");
		assertThat(s1.validate().hasErrors()).isFalse();
	}


	@Test
	public void validateInvalidUrl() throws Exception {
		Source s1 = new Source("name", "version", ".example.com", "email@example.com");
		assertThat(s1.validate().hasErrors()).isTrue();
		assertThat(s1.validate().getErrors().get(0)).isEqualTo("Invalid URL for source URL");
		assertThat(s1.validate().getErrorsJoined()).isEqualTo("Invalid URL for source URL");
	}


	@Test
	public void validateInvalidEmail() throws Exception {
		Source s1 = new Source("name", "version", "http://www.example.com", "example.com");
		assertThat(s1.validate().hasErrors()).isTrue();
		assertThat(s1.validate().getErrors().get(0)).isEqualTo("Invalid email-address for source email");
		assertThat(s1.validate().getErrorsJoined()).isEqualTo("Invalid email-address for source email");
	}


	@Test
	public void validateInvalidUrlEmail() throws Exception {
		Source s1 = new Source("name", "version", "example.com", "example.com");
		assertThat(s1.validate().hasErrors()).isTrue();
		assertThat(s1.validate().getErrors().get(0)).isEqualTo("Invalid URL for source URL");
		assertThat(s1.validate().getErrors().get(1)).isEqualTo("Invalid email-address for source email");
		assertThat(s1.validate().getErrorsJoined()).isEqualTo("Invalid URL for source URL, Invalid email-address for source email");
	}

}
