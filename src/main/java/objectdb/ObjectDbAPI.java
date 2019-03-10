package objectdb;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import data.Weather;
import data.WeatherEntry;

public class ObjectDbAPI {

    public static void main( String[] args ) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sql/weather.odb");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {            
            Weather w = new Weather(Calendar.getInstance().getTime());
            WeatherEntry we = new WeatherEntry("Temperature", "" + i, "°C");
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
