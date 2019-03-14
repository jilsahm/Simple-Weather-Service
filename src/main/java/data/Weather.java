package data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Weather {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();
    
    @Id
    @GeneratedValue
    private long               id;
    
    private Date               timestamp;
    private List<WeatherEntry> weatherEntries;
    
    public Weather(final Date timestamp) {
        this.timestamp      = timestamp;
        this.weatherEntries = new ArrayList<>();
    }
    
    public Weather addWeatherEntry(final WeatherEntry weatherEntry) {
        this.weatherEntries.add(weatherEntry);
        return this;
    }
    
    public Date getTimestamp() {
        return this.timestamp;
    }
    
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(String.format("%d Weather from %s:", this.id, DATE_FORMAT.format(this.timestamp), this.weatherEntries.size()));
        this.weatherEntries.forEach(entry -> output.append("\n ").append(entry));        
        return output.toString();
    }
}
