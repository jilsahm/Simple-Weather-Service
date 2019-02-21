package https;

import java.util.HashMap;
import java.util.Optional;

public class DataPackage {

    private String                  startline;
    private HashMap<String, String> headers;
    private Optional<String>        payload;
    
    private DataPackage(final ResponseBuilder responseBuilder) {
        this.startline = String.format("%s %d %s%n", responseBuilder.version, responseBuilder.statusCode, responseBuilder.statusMessage);
        this.headers   = responseBuilder.headers;
        this.payload   = responseBuilder.payload;
    }
    
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        
        output.append(this.startline);
        this.headers.forEach((key, value) -> output.append(String.format("%s: %s%n", key, value)));
        output.append("\n\r\n\r");
        output.append(this.payload);
        
        return output.toString();
    }
    
    public static class RequestBuilder {
        
        private String                  method;
        private String                  url;
        private HTTPVersion             version;
        private HashMap<String, String> headers;
        private String                  payload;
        
        public RequestBuilder() {}
        
    }
    
    public static class ResponseBuilder {
        private HTTPVersion             version;
        private int                     statusCode;
        private String                  statusMessage;
        private HashMap<String, String> headers;
        private Optional<String>        payload;
        
        public ResponseBuilder() {
            this.headers = new HashMap<>();
            this.payload = Optional.empty();
        }
        
        public ResponseBuilder setVersion(HTTPVersion version) {
            this.version = version;
            return this;
        }
        
        public ResponseBuilder whatStatusCode(final int statusCode) {
            this.statusCode = statusCode;
            return this;
        }
        
        public ResponseBuilder whatStatusMessage(final String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }
        
        public ResponseBuilder addHeader(final String key, final String value) {
            this.headers.put(key, value);
            return this;
        }
        
        public ResponseBuilder appendPayload(final String payload) {
            this.payload = Optional.of(payload);
            return this;
        }
        
        public DataPackage build() {
            return new DataPackage(this);
        }
    }
}
