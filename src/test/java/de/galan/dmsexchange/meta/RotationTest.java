package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT Rotation
 *
 * @author daniel
 */
public class RotationTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		assertThat(Rotation.DEGREE_0.getDegree()).isEqualTo(0);
		assertThat(Rotation.DEGREE_90.getDegree()).isEqualTo(90);
		assertThat(Rotation.DEGREE_180.getDegree()).isEqualTo(180);
		assertThat(Rotation.DEGREE_270.getDegree()).isEqualTo(270);
	}

}
