package app.seven.chew.auth

import app.seven.chew.auth.config.AppConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppConfig::class)
class ChewApplication

fun main(args: Array<String>) {
	runApplication<ChewApplication>(*args)
}
