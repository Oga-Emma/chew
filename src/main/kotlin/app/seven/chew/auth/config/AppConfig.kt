package app.seven.chew.auth.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
class AppConfig {
    @Value("\${app.rabbitmq.events.in.profile-updated}")
    lateinit var profileUpdatedEvent: String

    @Value("\${app.rabbitmq.events.out.account-created}")
    lateinit var accountCreatedEvent: String
}