package util;

import java.util.Date;

public class DateRange {

    private Date from;
    private Date to;
    
    /**
     * Creates a date range from 01.01.1970 to now.
     */
    public DateRange() {
        this(new Date(0), new Date());
    }
    
    /**
     * Creates a date range from x to now.
     * @param from From date (x)
     */
    public DateRange(final Date from) {
        this(from, new Date());
    }
    
    /**
     * Creates a daterange from x to y
     * @param from Date from (x)
     * @param to Date to (y)
     */
    public DateRange(final Date from, final Date to) {
        if (from.before(to)) {
            this.from = from;
            this.to   = to;
        } else {
            this.from = to;
            this.to   = from;
        }        
    }
    
    public boolean intersects(final Date date) {
        return this.from.getTime() <= date.getTime() && this.to.getTime() >= date.getTime();
    }
    
    public Date getFrom() {
        return this.from;
    }
    
    public Date getTo() {
        return this.to;
    }
    
}
