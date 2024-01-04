package app.seven.chew.auth.service

import app.seven.chew.auth.model.entity.AuthUser
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

interface EventPublisherService {
    fun publishUserCreatedEvent(user: AuthUser)
}

@Service
class RabbitMQEventPublisher(val rabbitTemplate: RabbitTemplate): EventPublisherService {
    val logger = LoggerFactory.getLogger(RabbitMQEventPublisher::class.java)

    override fun publishUserCreatedEvent(user: AuthUser) {
        logger.info("Publishing user created rabbitmq event for user: {}", user)
//        val event
//        rabbitTemplate.convertAndSend(event)
    }
}
