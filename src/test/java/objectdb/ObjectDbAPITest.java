package objectdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import data.Weather;
import data.WeatherEntry;
import util.DateRange;
import util.FileHandler;

@TestMethodOrder(OrderAnnotation.class)
public final class ObjectDbAPITest {

    private static final String TEST_DB_NAME = "testdb.odb";
    private static ObjectDbAPI  testDb;
    
    private final WeatherEntry[] testWeatherEntries = {
        new WeatherEntry("Blub",  9.5, "°Bu"),
        new WeatherEntry("Blob", -0.5, "°Bo"),
        new WeatherEntry("Blob", -0.8, "°Bo"),
        new WeatherEntry("Blob", -1.7, "°Bo")
    };
    
    private final Weather[] testWeathers = {
        new Weather(new Date(900)),
        new Weather(new Date(0)),
        new Weather(new Date(100))
    };
    
    @BeforeAll
    public static void initTestDb() {
        testDb = new ObjectDbAPI(TEST_DB_NAME);
    }
    
    @AfterAll
    public static void tearDown() {
        testDb.shutdown();
        new FileHandler().tearDown();
    }
    
    @Test
    @Order(1)
    public void testNumberOfEntities() {
        long actual = testDb.numberOfEntities(WeatherEntry.class);
        assertEquals(0, actual);
    }
    
    @Test
    @Order(2)
    public void testSaveEntity() {
        testDb.saveEntity(testWeatherEntries[0]);
        assertEquals(1, testDb.numberOfEntities(WeatherEntry.class));
    }   
    
    @Test
    @Order(3)
    public void testLoadEntity() {
        var testEntity = testDb.loadEntity(WeatherEntry.class, 1);
        assertTrue(testEntity.isPresent());
        assertEquals(testWeatherEntries[0], testEntity.get());
    }
    
    @Test
    @Order(4)
    public void testSaveAllEntries() {
        testDb.saveAllEntities(testWeatherEntries[1], testWeatherEntries[2], testWeatherEntries[3]);
        assertEquals(4, testDb.numberOfEntities(WeatherEntry.class));
    }
    
    @Test
    @Order(5)
    public void testLoadAllEntries() {
        var result = testDb.loadAllEntities(WeatherEntry.class);
        assertEquals(4, result.size());
        for (var i = 0; i < testWeatherEntries.length; i++) {
            assertEquals(testWeatherEntries[i], result.get(i));
        }
    }
    
    @Test
    @Order(6)
    public void testDeleteEntity() {
        assertEquals(4, testDb.numberOfEntities(WeatherEntry.class));
        var testEntity = testDb.loadEntity(WeatherEntry.class, 1);
        testDb.deleteEntity(testEntity.get());
        assertEquals(3, testDb.numberOfEntities(WeatherEntry.class));
    }
    
    @Test
    @Order(7)
    public void testDeleteAllEntities() {
        assertEquals(3, testDb.numberOfEntities(WeatherEntry.class));
        var result = testDb.loadAllEntities(WeatherEntry.class);
        testDb.deleteEntities(result.toArray());
        assertEquals(0, testDb.numberOfEntities(WeatherEntry.class));
    }
    
    @Test
    @Order(8)
    public void testFetchNewestWeatherData() {
        assertEquals(0, testDb.numberOfEntities(Weather.class));
        testDb.saveAllEntities(testWeathers);
        assertEquals(testWeathers.length, testDb.numberOfEntities(Weather.class));
        
        Optional<Weather> w = testDb.fetchNewestWeatherData();
        assertTrue(w.isPresent());
        assertEquals(900, w.get().getTimestamp().getTime());
    }
    
    @Test
    @Order(9)
    public void testFetchWeatherData() {
        DateRange     range = new DateRange(new Date(0), new Date(200));
        List<Weather> ws    = testDb.fetchWeatherData(range);
        
        assertEquals(2, ws.size());
        assertTrue(ws.stream().allMatch(element -> range.intersects(element.getTimestamp())));
    }
}
