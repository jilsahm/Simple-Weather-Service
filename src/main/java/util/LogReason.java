package util;

public enum LogReason {
    
    INFO  ("[INFO] "),
    DEBUG ("[DEBUG]"),
    ERROR ("[ERROR]");
    
    private final String consoleText;
    
    private LogReason (final String consoleText) {
        this.consoleText = consoleText;
    }
    
    @Override
    public String toString() {
        return this.consoleText;
    }
}
