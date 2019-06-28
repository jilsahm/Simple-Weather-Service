package weatherservice.openweather

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

data class Coordinate(
    var lon: Double,
    var lat: Double
)

data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)

data class Main(
    var temp: Double,
    var pressure: Int,
    var humidity: Int,
    var temp_min: Double,
    var temp_max: Double
)

data class Wind(
    var speed: Double,
    var deg: Int
)

data class Clouds(
    var all: Int
)

data class Sys(
    var type: Int,
    var id: Int,
    var message: Double,
    var country: String,
    var sunrise: Long,
    var sunset: Long
)

data class WeatherData(
    var coord: Coordinate,
    var weather: List<Weather>,
    var base: String,
    var main: Main,
    var visibility: Int,
    var wind: Wind,
    var clouds: Clouds,
    var dt: Long,
    var sys: Sys,
    var timezone: Int,
    var id: Long,
    var name: String,
    var cod: Int
) {
    override fun toString(): String {
        val mapper = ObjectMapper().registerModule(KotlinModule())
        return mapper.writeValueAsString(this)
    }
}
