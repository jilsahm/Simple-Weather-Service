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
        this.title = title;
        this.value = value;
        this.unit  = unit;
    }
  
    public String getTitle() { return this.title; }
    public double getValue() { return this.value; }
    public String getUnit()  { return this.unit; }    
    
    @Override
    public String toString() {        
        return String.format("%s: %.1f%s", this.title, this.value, this.unit);
    }
}
