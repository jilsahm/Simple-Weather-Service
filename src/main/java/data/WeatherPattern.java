package data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum WeatherPattern {

    CURRENT_TEMPERATUR_C ("Temperature", "°C", "(?<=id=\"wob_tm\"[^>]{0,50}>)[0-9]+"),
    CURRENT_PRECIPITATION ("Precipitation", "%", "(?<=id=\"wob_pp\"[^>]{0,50}>)[0-9]+"),
    CURRENT_HUMIDITY ("Humidity", "%", "(?<=id=\"wob_hm\"[^>]{0,50}>)[0-9]+"),
    CURRENT_WINDSTRENGTH ("Wind Strengh", "km/h", "(?<=id=\"wob_ws\"[^>]{0,50}>)[0-9]+");
    
    private final String  title;
    private final String  unit;
    private final Pattern pattern;
    
    private WeatherPattern(final String title, final String unit, final String expression) {
        this.title   = title;
        this.unit    = unit;
        this.pattern = Pattern.compile(expression);
    }
    
    public Optional<WeatherEntry> findWeatherEntry(final String rawWeatherData) {
        Matcher matcher = this.pattern.matcher(rawWeatherData);
        
        if (matcher.find()) {
            return Optional.of(new WeatherEntry(this.title, Long.parseLong(matcher.group()), this.unit));
        }       
        
        return Optional.empty();
    }
    
    public static List<WeatherEntry> findAllEntries(final String rawWeatherData) {
        return Arrays.stream(WeatherPattern.values())
            .map(pattern -> pattern.findWeatherEntry(rawWeatherData))
            .filter(entry -> entry.isPresent())
            .map(entry -> entry.get())
            .collect(Collectors.toList());
    }
}
