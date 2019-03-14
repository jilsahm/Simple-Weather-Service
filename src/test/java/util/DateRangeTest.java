package util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

public final class DateRangeTest {

    @Test
    public void testIntersects() {
        DateRange range1 = new DateRange();
        Date      date1  = new Date(0);
        assertTrue(range1.intersects(date1));
        
        DateRange range2 = new DateRange(new Date(100));
        Date      date2  = new Date(999);
        assertTrue(range2.intersects(date2));
        
        DateRange range3 = new DateRange(new Date(1), new Date(3));
        Date      date3  = new Date(3);
        assertTrue(range3.intersects(date3));
        
        DateRange range4 = new DateRange();
        Date      date4  = new Date(System.currentTimeMillis() + 1);
        assertFalse(range4.intersects(date4));
        
        DateRange range5 = new DateRange(new Date(100));
        Date      date5  = new Date(99);
        assertFalse(range5.intersects(date5));
        
        DateRange range6 = new DateRange(new Date(1), new Date(3));
        Date      date6  = new Date(4);
        assertFalse(range6.intersects(date6));
        
        DateRange range7 = new DateRange(new Date(3), new Date(1));
        Date      date7  = new Date(2);
        assertTrue(range7.intersects(date7));
        
        DateRange range8 = new DateRange(new Date(999), new Date(1));
        Date      date8  = new Date(0);
        assertFalse(range8.intersects(date8));
    }
    
}
