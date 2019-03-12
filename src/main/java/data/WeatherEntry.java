package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WeatherEntry {
    
    @Id
    @GeneratedValue
    private long   id;
    
    private String title;
    private double value;
    private String unit;
    
    public WeatherEntry(final String title, final double value, final String unit) {
        this(-1, title, value, unit);
    }
    
    public WeatherEntry(final long id, final String title, final double value, final String unit) {
        this.id    = id;
        this.title = title;
        this.value = value;
        this.unit  = unit;
    }
  
    public long   getId()    { return this.id; }
    public String getTitle() { return this.title; }
    public double getValue() { return this.value; }
    public String getUnit()  { return this.unit; }    
    
    @Override
    public boolean equals(Object other) {
        if (null != other && other instanceof WeatherEntry) {
            var o = (WeatherEntry)other;
            return this.title.equals(o.getTitle())
                && this.value == o.getValue()
                && this.unit.equals(o.getUnit());
        }
        return false;
    }
    
    @Override
    public String toString() {        
        return String.format("%s: %.1f%s", this.title, this.value, this.unit);
    }
}
