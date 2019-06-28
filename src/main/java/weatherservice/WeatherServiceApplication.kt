package weatherservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class WeatherServiceApplication

fun main(args: Array<String>) {
    runApplication<WeatherServiceApplication>(*args)
}
