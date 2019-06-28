package weatherservice.configuration

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import weatherservice.openweather.OpenWeatherEndpoint

@Component
class WeatherHandler(var endpoint: OpenWeatherEndpoint) {

    fun consume(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse
            .ok()
            .contentType(MediaType.TEXT_HTML)
            .render("weather",hashMapOf("weather" to endpoint.fetch()))
}