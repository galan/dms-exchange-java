package de.galan.dmsexchange.meta;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT DocumentFile
 *
 * @author daniel
 */
public class DocumentFileTest extends AbstractTestParent {

	@Test
	public void equalityConstructor() throws Exception {
		DocumentFile d1 = new DocumentFile();
		d1.setFilename("myfile.pdf");
		DocumentFile d2 = new DocumentFile("myfile.pdf");
		assertThat(d1).isEqualTo(d2);
	}

}
