package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Butler {

    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    
    private Butler() {}
        
    public static synchronized void log(final LogReason logReason, final Object text) {
        System.out.printf("%s  %s %s%n", DATETIME_FORMAT.format(new Date()), logReason, text.toString());
    }
    
}
