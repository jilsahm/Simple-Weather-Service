package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import util.Butler;
import util.LogReason;

public final class DatabaseAPI {

    private static final String      DB_NAME           = "weather.db";
    private static final String      CONNECTION_STRING = "jdbc:sqlite:";
    private static final String      DRIVER_NAME       = "org.sqlite.JDBC";
    private static final DatabaseAPI INSTANCE          = new DatabaseAPI();
    
    private DatabaseDetails databaseDetails;
    private Connection      connection;
    
    private DatabaseAPI() {
        this.databaseDetails = new DatabaseDetails(DB_NAME);
        try {
            Class.forName(DRIVER_NAME);
            this.connection = DriverManager.getConnection(CONNECTION_STRING + this.databaseDetails.getAbsolutePath());
            Butler.log(LogReason.INFO, String.format("Successfully connected to database %s.", DB_NAME));
        } catch (SQLException e) {
            Butler.log(LogReason.ERROR, String.format("Coult not connect database %s.", DB_NAME));
        } catch (ClassNotFoundException e) {
            Butler.log(LogReason.ERROR, String.format("Driverclass %s not found.", DRIVER_NAME));
        }
        this.setupDatabaseStructure();
    }
    
    private void setupDatabaseStructure() {
        String structure = Butler.loadResourceFromJar("sql/dbstructure.sql").get();
        try (Statement statement = this.connection.createStatement()){            
            statement.execute(structure);            
        } catch (SQLException e) {            
            Butler.log(LogReason.ERROR, "Failed to initialize database structure.");
            e.printStackTrace();
        }
        Butler.log(LogReason.INFO, "Database structur initialized.");
    }
    
    @Override
    protected void finalize() throws Throwable {
        if (null != this.connection) {
            this.connection.close();
        }
    }
    
    public static DatabaseAPI getInstance() {
        return INSTANCE;
    }
    
    public static void main( String[] args ) throws ClassNotFoundException {
        final String rootPath = DatabaseAPI.class.getResource("").getPath();
    }
}
