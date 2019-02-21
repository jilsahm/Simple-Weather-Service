package data;

import java.util.ArrayList;
import java.util.Optional;

public class WeatherDataHandler {

    private ArrayList<WeatherEntry> gentrifiedWeatherData;
    
    public WeatherDataHandler() {
        this(null);
    }
    
    public WeatherDataHandler(String rawWeatherData) {
        
    }
    
    public Optional<WeatherEntry> getWeatherEntry(final String title){
        for (final WeatherEntry entry: this.gentrifiedWeatherData) {
            if (entry.getTitle().equals(title)){
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }
    
}
