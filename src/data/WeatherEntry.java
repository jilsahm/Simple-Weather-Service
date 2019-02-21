package data;

public class WeatherEntry {

    private String title;
    private String value;
    private String unit;
    
    public WeatherEntry(final String title, final String value, final String unit) {
        this.title = title;
        this.value = value;
        this.unit  = unit;
    }

    public String getTitle() {
        return this.title;
    }

    public String getValue() {
        return this.value;
    }

    public String getUnit() {
        return this.unit;
    }    
    
    @Override
    public String toString() {        
        return String.format("%s: %s%s", this.title, this.value, this.unit);
    }
}
