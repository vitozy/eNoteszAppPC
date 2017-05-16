
package hu.vitamas.enotesz.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static hu.vitamas.enotesz.util.RegistrationFormValidator.newValidator;

/**
 * @author vitozy
 *
 */
public class RegistrationFormValidatorTest {
	
	@Test
	public void testNameFieldHasText() {
		Boolean case1 = newValidator().checkName("").isValid();
		Boolean case2 = newValidator().checkName("Teszt név").isValid();
		
		assertFalse("test names : case1", case1);
		assertTrue("test names : case2", case2);
	}

	@Test
	public void testEmailFieldHasText() {
		Boolean case1 = newValidator().checkEmail("posta@vitamas.hu").isValid();
		Boolean case2 = newValidator().checkEmail("").isValid();
		
		assertTrue("test email : case1", case1);
		assertFalse("test email : case1", case2);
	}
	
	@Test
	public void testPasswordFieldMinLength() {
		Boolean ok = newValidator().checkPasswords("12345678","12345678").isValid();
		Boolean all_not = newValidator().checkPasswords("1234","1234").isValid();
		Boolean first_not = newValidator().checkPasswords("123","12345678").isValid();
		Boolean second_not = newValidator().checkPasswords("12345678","123").isValid();
		
		assertTrue("pass : ok", ok);
		assertFalse("pass: not", all_not);
		assertFalse("pass : first invalid", first_not);
		assertFalse("pass : second invalid", second_not);
	}
	
	@Test
	public void testPasswordFieldEquals() {
		Boolean ok = newValidator().checkPasswords("12345678","12345678").isValid();
		Boolean not = newValidator().checkPasswords("12345678","012345678").isValid();
		
		assertTrue("pass : ok", ok);
		assertFalse("pass: not", not);
	}
	
	@Test
	public void testFull() {
		Boolean ok = newValidator()
				.checkName("Teszt")
				.checkEmail("posta@vitamas.hu")
				.checkPasswords("tesztjelszo","tesztjelszo")
				.isValid();
		Boolean notOk = newValidator()
				.checkName("Teszt")
				.checkEmail("")
				.checkPasswords("tesztjelszo","tesztjelszo")
				.isValid();
		
		assertTrue("full is ok", ok);
		assertFalse("full is not ok", notOk);
	}
}
