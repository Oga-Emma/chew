package app.seven.chew

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChewApplication

fun main(args: Array<String>) {
	runApplication<ChewApplication>(*args)
}
