package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.Butler;
import util.LogReason;

public class RestServer {

    private final int       port;
    private ServerSocket    serverSocket;
    private ExecutorService threadPool;
    
    public RestServer (final int port) {
        this.port = port;
        this.threadPool = Executors.newCachedThreadPool();
    }
    
    public void start() {
        this.initSocket();
        Butler.log(LogReason.INFO, "Server is listening...");
        
        while (true) {
            try {
                this.threadPool.execute(new RestWeatherController(this.serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        if (this.serverSocket != null && !this.serverSocket.isClosed()) {
            this.serverSocket.close();
        }
    }
    
    private void initSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            Butler.log(LogReason.INFO, "ServerSocket bound to port " + this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main( String[] args ) {
        Butler.log(LogReason.INFO, "WeatherService is starting...");
        new RestServer(9808).start();
    }
}
