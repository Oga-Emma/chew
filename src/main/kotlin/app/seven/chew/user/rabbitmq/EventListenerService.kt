package app.seven.chew.auth.rabbitmq

import app.seven.chew.user.business.UserProfileBusiness
import app.seven.chew.user.model.UserDto
import app.seven.chew.user.utils.Constants
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar
import org.springframework.stereotype.Service


@Service
class RabbitMQEventListener(val userBusiness: UserProfileBusiness): RabbitListenerConfigurer {
    val logger = LoggerFactory.getLogger(RabbitMQEventListener::class.java)

    //    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    @RabbitListener(queues = [Constants.USER_CREATED_QUEUE])
    fun receiveMessage(message: UserDto) {
        logger.info("User updated event received {}", message)
        userBusiness.handleUserCreatedEvent(message)
    }

    override fun configureRabbitListeners(p: RabbitListenerEndpointRegistrar) {}

}
