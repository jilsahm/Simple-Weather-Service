package weatherservice.openweather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class OpenWeatherEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherEndpoint.class);

    private final WebClient webClient;
    private final String    apiKey;

    @Autowired
    public OpenWeatherEndpoint(final @Value("${apikey}") String apiKey) {
        this.webClient = WebClient
            .builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather")
            .build();

        this.apiKey = Objects.requireNonNull(apiKey, "apiKey must not be null");
    }

    public Mono<WeatherData> fetch() {
        return this.webClient
            .get()
            .uri(uriBuilder -> uriBuilder
                    .queryParam("q", "Berlin,de")
                    .queryParam("appid", this.apiKey)
                    .build())
            .exchange()
            .flatMap(response -> response.bodyToMono(WeatherData.class));
    }

}
