package app.seven.chew.user

import app.seven.chew.user.config.AppConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppConfig::class)
class ChewApplication

fun main(args: Array<String>) {
	runApplication<ChewApplication>(*args)
}
