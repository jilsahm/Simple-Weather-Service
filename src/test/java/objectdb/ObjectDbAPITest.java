package objectdb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import data.WeatherEntry;
import util.FileHandler;

@TestMethodOrder(OrderAnnotation.class)
public final class ObjectDbAPITest {

    private static final String TEST_DB_NAME = "testdb.odb";
    private static ObjectDbAPI  testDb;
    
    private final WeatherEntry[] testData = {
        new WeatherEntry(1, "Blub",  9.5, "°Bu"),
        new WeatherEntry(2, "Blob", -0.5, "°Bo"),
        new WeatherEntry(3, "Blob", -0.8, "°Bo"),
        new WeatherEntry(4, "Blob", -1.7, "°Bo")
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
        testDb.saveEntity(testData[0]);
        assertEquals(1, testDb.numberOfEntities(WeatherEntry.class));
    }   
    
    @Test
    @Order(3)
    public void testLoadEntity() {
        WeatherEntry testEntity = testDb.loadEntity(WeatherEntry.class, 1);
        assertEquals(testData[0], testEntity);
    }
    
    @Test
    @Order(4)
    public void testSaveAllEntries() {
        testDb.saveAllEntities(testData[1], testData[2], testData[3]);
        assertEquals(4, testDb.numberOfEntities(WeatherEntry.class));
    }
    
    @Test
    @Order(5)
    public void testLoadAllEntries() {
        var result = testDb.loadAllEntities(WeatherEntry.class);
        assertEquals(4, result.size());
        for (var i = 0; i < testData.length; i++) {
            assertEquals(testData[i], result.get(i));
        }
    }
    
    @Test
    @Order(6)
    public void testDeleteEntity() {
        assertEquals(4, testDb.numberOfEntities(WeatherEntry.class));
        testDb.deleteEntity(testData[0]);
        assertEquals(3, testDb.numberOfEntities(WeatherEntry.class));
    }
    
    @Test
    @Order(7)
    public void testDeleteAllEntities() {
        assertEquals(3, testDb.numberOfEntities(WeatherEntry.class));
        testDb.deleteEntities(testData[1], testData[2], testData[3]);
        assertEquals(0, testDb.numberOfEntities(WeatherEntry.class));
    }
}
