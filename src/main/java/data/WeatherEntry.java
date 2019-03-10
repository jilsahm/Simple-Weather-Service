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
    private String value;
    private String unit;
    
    public WeatherEntry(final String title, final String value, final String unit) {
        this.title = title;
        this.value = value;
        this.unit  = unit;
    }

    public long   getId()    { return this.id; }    
    public String getTitle() { return this.title; }
    public String getValue() { return this.value; }
    public String getUnit()  { return this.unit; }    
    
    @Override
    public String toString() {        
        return String.format("ID: %d, %s: %s%s", this.id, this.title, this.value, this.unit);
    }
}
