package util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ButlerTest {
    
    @Test
    public void testLoadResource() {
        final String validPath = "valid.html";
        assertTrue(Butler.loadResource(validPath).isPresent());
        
        final String invalidPath = "not.present";
        assertThrows(NullPointerException.class, () -> Butler.loadResource(invalidPath));
    }
    
    @Test
    public void testLoadResourceFromJar() {
        final String validPath = "sql/dbstructure.sql";
        assertNotEquals("", Butler.loadResourceFromJar(validPath));
        
        final String invalidPath = "not.present";
        assertThrows(NullPointerException.class, () -> Butler.loadResourceFromJar(invalidPath));
    }
}
