package https;

public enum HTTPVersion {

    HTTP_1 ("HTTP/1.1"),
    HTTP_2 ("HTTP/2.1");
    
    private final String version;
    
    private HTTPVersion(final String version) {
        this.version = version;
    }
    
    @Override
    public String toString() {
        return this.version;
    }
}
