package data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import util.Butler;

public final class WeatherPatternTest {
    
    private static String validHTML;
    private static String invalidHTML;
    
    @BeforeAll
    public static void loadDummyWeatherData() {
        validHTML   = Butler.loadResource("valid.html").orElse("Test will fail");
        invalidHTML = Butler.loadResource("invalid.html").orElse("Test will fail");
    }
    
    @Test
    public void testFindWeatherEntry() {
        assertTrue(WeatherPattern.CURRENT_TEMPERATUR_C.findWeatherEntry(validHTML).isPresent());
        assertTrue(WeatherPattern.CURRENT_PRECIPITATION.findWeatherEntry(validHTML).isPresent());
        assertTrue(WeatherPattern.CURRENT_HUMIDITY.findWeatherEntry(validHTML).isPresent());
        assertTrue(WeatherPattern.CURRENT_WINDSTRENGTH.findWeatherEntry(validHTML).isPresent());
        
        assertFalse(WeatherPattern.CURRENT_TEMPERATUR_C.findWeatherEntry(invalidHTML).isPresent());
        assertFalse(WeatherPattern.CURRENT_PRECIPITATION.findWeatherEntry(invalidHTML).isPresent());
        assertFalse(WeatherPattern.CURRENT_HUMIDITY.findWeatherEntry(invalidHTML).isPresent());
        assertFalse(WeatherPattern.CURRENT_WINDSTRENGTH.findWeatherEntry(invalidHTML).isPresent());
    }
    
    @Test
    public void testFindAllEntries() {
        assertTrue(0 < WeatherPattern.findAllEntries(validHTML).size());
        assertFalse(0 < WeatherPattern.findAllEntries(invalidHTML).size());
    }
    
}
