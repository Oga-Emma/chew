package app.seven.chew.auth.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
class AppConfig {
    @Value("\${rabbitmq.events.in.profile-updated}")
    lateinit var profileUpdatedEvent: String

    @Value("\${rabbitmq.events.out.account-created}")
    lateinit var accountCreatedEvent: String
}