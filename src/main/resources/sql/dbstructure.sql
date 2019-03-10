CREATE TABLE IF NOT EXISTS Weather (
    WeatherID       INTEGER NOT NULL,
    MeasurementDate TEXT NOT NULL,
    
    PRIMARY KEY (WeatherID)
);

CREATE TABLE IF NOT EXISTS Unit(
    UnitID      INTEGER NOT NULL,
    Title       TEXT NOT NULL,
    UnitSymbole TEXT NOT NULL,
    
    PRIMARY KEY (UnitID)
);

CREATE TABLE IF NOT EXISTS WeatherEntry (
    WeatherEntryID INTEGER NOT NULL,
    WeatherID      INTEGER NOT NULL,
    UnitID         INTEGER NOT NULL,
    Value          REAL NOT NULL,
    
    PRIMARY KEY (WeatherEntryID),
    FOREIGN KEY (WeatherID) REFERENCES Weather(WeatherID),
    FOREIGN KEY (UnitID) REFERENCES Unit(UnitID)
);