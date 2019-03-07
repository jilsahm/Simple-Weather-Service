package util;

import java.io.IOException;
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
    
    private Butler() {}
        
    public static synchronized void log(final LogReason logReason, final Object text) {
        System.out.printf("%s  %s %s%n", DATETIME_FORMAT.format(new Date()), logReason, text.toString());
    }
    
    public static Optional<String> loadFile(final Class<?> rootPoint, final String relativeFromRootPoint) {
        String rawPath  = rootPoint.getClassLoader().getResource(relativeFromRootPoint).getPath();
        Path   filePath = FileSystems.getDefault().getPath(rawPath.substring(1));
        
        try {
            return Optional.ofNullable(
                Files.readAllLines(filePath)
                .stream()
                .collect(Collectors.joining(NEWLINE))
            );
        } catch (IOException e) {
            log(LogReason.ERROR, String.format("File %s could not read.", relativeFromRootPoint));
        }  
        return Optional.empty();
    }
    
}
