package weatherservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Mono
import weatherservice.openweather.OpenWeatherEndpoint

@Configuration
open class WeatherRouter(var weatherHandler: WeatherHandler) {

    @Bean
    open fun route() = router{ GET("/").invoke(weatherHandler::consume) }

}
