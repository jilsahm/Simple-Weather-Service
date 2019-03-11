package objectdb;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import data.Weather;
import data.WeatherEntry;
import util.Butler;
import util.FileHandler;
import util.LogReason;

public class ObjectDbAPI {

    private static final String WEATHER_ODB_NAME = "weather.odb";
    private static final String WEATHER_ODB_PATH = Path.of(new FileHandler().getAbsolutePathOfRootDirectory().toString(), WEATHER_ODB_NAME).toString();
    
    private EntityManagerFactory managerFactory;
    private EntityManager        entityManager;
    
    public ObjectDbAPI() {
        this(WEATHER_ODB_NAME);
    }
    
    public ObjectDbAPI(final String dbName) {
        this.managerFactory = Persistence.createEntityManagerFactory(dbName);
        this.entityManager  = this.managerFactory.createEntityManager();
    }
    
    @Override
    protected void finalize() throws Throwable {
        if (null != this.entityManager && this.entityManager.isOpen()) {
            this.entityManager.close();
        }
        if (null != this.managerFactory && this.managerFactory.isOpen()) {
            this.managerFactory.close();
        }
    }
    
    public final <T> void saveEntity(T entity) {
        this.saveAllEntities(entity);
    }
    
    @SafeVarargs
    public final <T> void saveAllEntities(T ...entities) {
        try {
            this.entityManager.getTransaction().begin();
            for (T entity : entities) {
                try {
                    this.entityManager.persist(entity);
                } catch(EntityExistsException e) {
                    Butler.log(LogReason.ERROR, String.format("Entity %s@%d allready exits.", entity.getClass().getName(), entity.hashCode()));
                }
            }            
            this.entityManager.getTransaction().commit();
        } catch(IllegalArgumentException e) {        
            Butler.log(LogReason.ERROR, "Entities class is not persistable.");
        }
    }
    
    public final <T> T loadEntity(final Class<T> type, final long id) {
        TypedQuery<T> query = this.entityManager.createQuery(String.format("SELECT * FROM %s WHERE id = %d", type.getName(), id), type);
        return query.getSingleResult();
    }
    
    public final <T> List<T> loadAllEntities(final Class<T> type) {
        TypedQuery<T> query = this.entityManager.createQuery("SELECT * FROM " + type.getName(), type);
        return query.getResultList();
    }
    
    public static void main( String[] args ) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(WEATHER_ODB_PATH);
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {            
            Weather w = new Weather(Calendar.getInstance().getTime());
            WeatherEntry we = new WeatherEntry("Temperature", i, "°C");
            w.addWeatherEntry(we);
            
            em.persist(w);
        }
        em.getTransaction().commit();

        // Find the number of Point objects in the database:
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Weather p");
        System.out.println("Total WeatherEntries: " + q1.getSingleResult());

        // Retrieve all the Point objects from the database:
        TypedQuery<Weather> query = em.createQuery("SELECT p FROM Weather p", Weather.class);
        List<Weather> results = query.getResultList();
        for (Weather p : results) {
            System.out.println(p);
        }

        // Close the database connection:
        em.close();
        emf.close();
    }
    
}
