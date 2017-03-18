package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;


/**
 * CUT User
 */
public class UserTest {

	@Test
	public void equality() throws Exception {
		User u1 = new User("email@example.com");
		User u2 = new User("email@example.com");
		User u3 = new User("invalid.example.com");
		assertThat(u1).isEqualTo(u2);
		assertThat(u1).isNotEqualTo(u3);
	}


	@Test
	public void toStringTest() throws Exception {
		User u1 = new User("email@example.com");
		assertThat(u1.toString()).isEqualTo("email@example.com");
	}


	@Test
	public void validateOk() throws Exception {
		User u1 = new User("email@example.com");
		assertThat(u1.validate().hasErrors()).isFalse();
	}


	@Test
	public void validateInvalidEmail() throws Exception {
		User u1 = new User("invalid example.com");
		assertThat(u1.validate().hasErrors()).isTrue();
		assertThat(u1.validate().getErrors().get(0)).isEqualTo("Invalid email for user 'invalid example.com'");
		assertThat(u1.validate().getErrorsJoined()).isEqualTo("Invalid email for user 'invalid example.com'");
	}

}
