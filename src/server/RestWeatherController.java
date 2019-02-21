package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import util.Butler;
import util.LogReason;

public class RestWeatherController extends Thread{

    private final static int BUFFER_SIZE = 1024;
    
    private Socket connectedClient;
    
    public RestWeatherController(final Socket connectedClient) {
        this.connectedClient = connectedClient;
    }
    
    @Override
    public void run() {
        Butler.log(LogReason.INFO, String.format("%s connected", this.connectedClient.getInetAddress().getHostAddress()));
        try {
            String payload = this.obtainHttpRequest();
            Butler.log(LogReason.DEBUG, payload);
            this.connectedClient.close();
            Butler.log(LogReason.DEBUG, String.format("%s disconnected", this.connectedClient.getInetAddress().getHostAddress()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String obtainHttpRequest() throws IOException {
        BufferedInputStream in      = new BufferedInputStream(this.connectedClient.getInputStream());
        StringBuilder       payload = new StringBuilder();
        byte[]              buffer  = new byte[BUFFER_SIZE];
        
        int bytesRead = in.read(buffer);
        for (int i = 0; i < bytesRead; i++) {
            payload.append((char)buffer[i]);
        }
        
        Butler.log(LogReason.DEBUG, payload.toString());
        return payload.toString();
    }
    
}
