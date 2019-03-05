package https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import data.WeatherPattern;

public class HttpsConnector {

	private static final String GOOGLE = "https://www.google.com/search?q=wetter";
	
	private URL url;
	
	public HttpsConnector(){
		try {
			this.url = new URL(GOOGLE);
		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		}
	}
	
	public String requestRawWeatherData(){
		StringBuilder      payload = new StringBuilder();
		BufferedReader     reader;
		HttpsURLConnection connection;
		
		try {
			connection = (HttpsURLConnection)url.openConnection();
			reader     = new BufferedReader(new InputStreamReader(connection.getInputStream()));;
		
			reader.lines().forEach(line -> payload.append(line));			
			reader.close();
		} catch (IOException e) {}
		
		return payload.toString();
	}
	
	
	public static void main( String[] args ) {
	    System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0");
	    String weather = new HttpsConnector().requestRawWeatherData();
	    //System.out.println(weather);
        WeatherPattern.findAllEntries(weather).forEach(System.out::println);
    }
}
