
package hu.vitamas.enotesz.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import org.junit.Test;
import static hu.vitamas.enotesz.util.EventFormValidator.newValidator;

/**
 * @author vitozy
 *
 */
public class EventFormValidatorTest {
	
	@Test
	public void testOnlyName() {
		Boolean case1 = newValidator().checkName("").isValid();
		Boolean case2 = newValidator().checkName("Teszt név").isValid();
		
		assertFalse("test names : case1", case1);
		assertTrue("test names : case2", case2);
	}

	@Test
	public void testOnlyTimes() {
		Boolean case1 = newValidator().checkTimes("10:00","25:00").isValid();
		Boolean case2 = newValidator().checkTimes("10:00","20:00").isValid();
		
		assertFalse("test times : case1", case1);
		assertTrue("test times : case1", case2);
	}
	
	@Test
	public void testOnlyDates() {
		Boolean oneDayEvent = newValidator().checkDates(LocalDate.parse("2017-04-27"),LocalDate.parse("2017-04-27")).isValid();
		Boolean secondMustAfterOrEq = newValidator().checkDates(LocalDate.parse("2017-04-27"),LocalDate.parse("2017-04-26")).isValid();
		Boolean mustNotNull = newValidator().checkDates(null,null).isValid();
		Boolean moreDayEvent = newValidator().checkDates(LocalDate.parse("2017-04-27"),LocalDate.parse("2017-04-29")).isValid();
		
		assertTrue("onedayevent dates", oneDayEvent);
		assertFalse("second date must after or equal", secondMustAfterOrEq);
		assertFalse("days must be not null", mustNotNull);
		assertTrue("moredayevent dates", moreDayEvent);
	}
	
	@Test
	public void testFull() {
		Boolean ok = newValidator()
				.checkName("Teszt")
				.checkDates(LocalDate.parse("2017-04-27"),LocalDate.parse("2017-04-27"))
				.checkTimes("10:00","20:00")
				.isValid();
		Boolean notOk = newValidator()
				.checkName("Teszt")
				.checkDates(null,LocalDate.parse("2017-04-27"))
				.checkTimes("10:00","20:00")
				.isValid();
		
		assertTrue("full is ok", ok);
		assertFalse("full is not ok", notOk);
	}
}
