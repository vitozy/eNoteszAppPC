package hu.vitamas.enotesz.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class DateTimeValidatorTest {

	public static List<String> valids;
	public static List<String> invalids;
	
	@BeforeClass
	public static void init() {
		//five-five
		valids = Arrays.asList("10:00","01:59","15:20","19:07","23:59");
		invalids = Arrays.asList("1:1","10","10:60","25:00","100:10");
	}
	
	@Test
	public void testValids() {
		valids.stream().forEach(item -> assertTrue(DateTimeValidator.isValidTime(item)));
	}

	@Test
	public void testInvalids() {
		invalids.stream().forEach(item -> assertFalse(DateTimeValidator.isValidTime(item)));
	}
}
