package database;

import java.nio.file.Path;

import util.FileHandler;

public final class DatabaseDetails {

    private final String      dbname;
    private final FileHandler location;
    
    public DatabaseDetails(final String dbname) {
        this.dbname   = dbname;
        this.location = new FileHandler();
        this.createIfNotExists();
    }
    
    private void createIfNotExists() {
        this.location.createFile(this.dbname);
    }
    
    public String getAbsolutePath() {
        return Path.of(this.location.toString(), this.dbname).toString();
    }
    
    public static void main( String[] args ) {
        new DatabaseDetails("HELLO.db");
    }
}
