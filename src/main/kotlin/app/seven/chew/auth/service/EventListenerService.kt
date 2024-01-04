package app.seven.chew.auth.service

import app.seven.chew.auth.model.dto.UserDto
import app.seven.chew.auth.utils.Constants
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar
import org.springframework.stereotype.Service

interface EventListenerService {
    fun handleUserUpdatedEvent(event: UserDto)
}

@Service
class RabbitMQEventListener(val userService: UserService): EventListenerService, RabbitListenerConfigurer {
    val logger = LoggerFactory.getLogger(RabbitMQEventListener::class.java)

    @RabbitListener(queues = [Constants.USER_UPDATED_QUEUE])
//    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    fun receiveMessage(message: UserDto) {
        logger.info("User updated event received {}", message)
        handleUserUpdatedEvent(message)
    }

    override fun handleUserUpdatedEvent(event: UserDto) {
        userService.updateUser(event)
    }

    override fun configureRabbitListeners(p: RabbitListenerEndpointRegistrar) {}

}
