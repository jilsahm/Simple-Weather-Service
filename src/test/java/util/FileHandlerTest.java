package util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileHandlerTest {
    
    private static final String      MEMBER_NAME = "pathToExternalDirectory";    
    private static       FileHandler fileHandler;    
    
    @BeforeAll
    public static void init() {
        fileHandler = new FileHandler();
    }
    
    @AfterAll
    public static void tearDown() {
        if (null != fileHandler) {
            fileHandler.tearDown();
        }
    }
    
    @Test
    public void testFileHandler() {        
        try {
            Field path = fileHandler.getClass().getDeclaredField(MEMBER_NAME);
            path.setAccessible(true);
            File file = Path.of(path.get(fileHandler).toString()).toFile();
            
            assertTrue(file.exists() && file.isDirectory());
            
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            fail(String.format("Reflection failed. Reason: %s", e.toString()));
        }        
    }
    
    @Test
    public void testCreateFile() {
        final String filename = "test.file";
        fileHandler.createFile(filename);
        File createdFile = Path.of(fileHandler.getAbsolutePathOfRootDirectory(), filename).toFile();
        
        assertTrue(createdFile.exists());
    }
    
    @Test
    public void testDeleteFile() {
        final String filename = "test.file";
        fileHandler.createFile(filename);
        File createdFile = Path.of(fileHandler.getAbsolutePathOfRootDirectory(), filename).toFile();        
        assertTrue(createdFile.exists());
        
        fileHandler.deleteFile(filename);
        assertFalse(createdFile.exists());
    }
}
