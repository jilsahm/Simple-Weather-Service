package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Butler {

    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    private static final String           NEWLINE         = System.getProperty("line.separator");
    private static final int              UNJARED         = 1;
    private static final int              JARED           = 6;
    
    private Butler() {}
        
    public static synchronized void log(final LogReason logReason, final Object text) {
        System.out.printf("%s  %s %s%n", DATETIME_FORMAT.format(new Date()), logReason, text.toString());
    }
    
    public static Optional<String> loadResource(final String resourceName) {
        String rawPath  = Butler.modifyPath(Butler.class.getClassLoader().getResource(resourceName).getPath());        
        Path   filePath = FileSystems.getDefault().getPath(rawPath);
        
        try {
            return Optional.ofNullable(
                Files.readAllLines(filePath)
                .stream()
                .collect(Collectors.joining(NEWLINE))
            );
        } catch (IOException e) {
            log(LogReason.ERROR, String.format("Resource %s could not read.", resourceName));
        }  
        return Optional.empty();
    }
    
    private static String modifyPath(final String path) {
        if (path.startsWith("/")) {
            return path.substring(UNJARED);
        } else {
            return path.substring(JARED);
        }
    }
    
    public static Optional<String> loadResourceFromJar(final String resourceName) {         
        try (
            InputStream       inputStream       = Butler.class.getClassLoader().getResourceAsStream(resourceName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader    bufferedReader    = new BufferedReader(inputStreamReader); 
            ){   
            
            return Optional.of(bufferedReader.lines().collect(Collectors.joining(NEWLINE)));
            
        } catch (IOException e) {
            Butler.log(LogReason.ERROR, String.format("Error reading file %s", resourceName));
        }
        return Optional.empty();
    }    
}
