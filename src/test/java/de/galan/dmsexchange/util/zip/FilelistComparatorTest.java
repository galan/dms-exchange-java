package de.galan.dmsexchange.util.zip;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;

import com.google.common.collect.Lists;

import de.galan.commons.logging.Logr;
import de.galan.commons.test.AbstractTestParent;


/**
 * CUT FilelistComparator
 *
 * @author daniel
 */
public class FilelistComparatorTest extends AbstractTestParent {

	private static final Logger LOG = Logr.get();


	@Test
	public void testSort() throws Exception {
		FilelistComparator fc = new FilelistComparator();
		List<String> list = Lists.newArrayList("/path1/", "/file2", "/path2/", "/file1", "/aa/", "/aaa", "/zzz");
		list.sort(fc);
		LOG.info("{}", list);
		assertThat(list).containsExactly("/aaa", "/file1", "/file2", "/zzz", "/aa/", "/path1/", "/path2/");
	}

}
