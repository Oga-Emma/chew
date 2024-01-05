package app.seven.chew.auth.rabbitmq

import app.seven.chew.user.model.UserDto
import app.seven.chew.user.model.entity.UserProfile
import app.seven.chew.user.utils.Constants
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

interface EventPublisherService {
    fun publishUserUpdatedEvent(userDto: UserDto)
}

@Service
class RabbitMQEventPublisher(val rabbitTemplate: RabbitTemplate) : EventPublisherService {
    val logger = LoggerFactory.getLogger(RabbitMQEventPublisher::class.java)

    override fun publishUserUpdatedEvent(userDto: UserDto) {
        logger.info("Publishing user updated rabbitmq event for user: {}", userDto)
        rabbitTemplate.convertAndSend(Constants.CHEW_USER_UPDATED_MESSAGE_TOPIC, userDto)
    }
}
