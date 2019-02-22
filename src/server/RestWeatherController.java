package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import https.DataPackage;
import https.HTTPVersion;
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
            this.sendResponse();
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
        
        //Butler.log(LogReason.DEBUG, payload.toString());
        return payload.toString();
    }
    
    private void sendResponse() throws IOException {
        BufferedOutputStream out      = new BufferedOutputStream(this.connectedClient.getOutputStream());        
        DataPackage          response = new DataPackage.ResponseBuilder()
            .setVersion(HTTPVersion.HTTP_1)
            .whatStatusCode(200)
            .whatStatusMessage("OK")
            .addHeader("Content-length", "1000")
            .addHeader("Content-type", "text/json")
            .appendPayload("{ \"simple\" : \"JSON\" }")
            .build();
        
        Butler.log(LogReason.DEBUG, response.toString());
        byte[] buffer = response.toString().getBytes();
        out.write(buffer);
        out.close();
        Butler.log(LogReason.DEBUG, "Response sent to client");
    }
}
