package de.mh.walter.boundary;

import org.junit.Test;
import static org.junit.Assert.*;

public class AccountControllerTest {
    
    @Test
    public void testValidEmailAddress() {
        AccountController instance = new AccountController();
        
        assertTrue(instance.validEmailAddress("test@example.com"));
        assertTrue(instance.validEmailAddress("test.test@example.com"));
        assertTrue(instance.validEmailAddress("test-test@example.com"));
        assertTrue(instance.validEmailAddress("test-test@example-test.com"));
        assertTrue(instance.validEmailAddress("test-test@example.test.com"));
        assertTrue(instance.validEmailAddress("test.test@example-test.com"));
        assertTrue(instance.validEmailAddress("test.test@example-test.test.com"));
        assertTrue(instance.validEmailAddress("test_.-test@example-test.test.com"));
        
        assertFalse(instance.validEmailAddress("test"));
        assertFalse(instance.validEmailAddress("test@example"));
        assertFalse(instance.validEmailAddress("test@example-com"));
        assertFalse(instance.validEmailAddress("example.com"));
        assertFalse(instance.validEmailAddress("test@@example.com"));
        assertFalse(instance.validEmailAddress("test@test@example.com"));
    }
}
