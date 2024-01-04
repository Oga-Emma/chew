package app.seven.chew.auth.rabbitmq

import app.seven.chew.auth.business.UserBusiness
import app.seven.chew.auth.rabbitmq.model.ProfileUpdatedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class ProfileUpdatedEventRabbitMqListener(val userBusiness: UserBusiness) {
    var logger: Logger = LoggerFactory.getLogger(ProfileUpdatedEventRabbitMqListener::class.java)

//    @RabbitListener(queues = [Constants.PROFILE_UPDATED_EVENT])
    fun receiveMessage(event: ProfileUpdatedEvent) {
        logger.info("Received profile updated event from AMQP: {}", event.toString())

        userBusiness.updateDetails(event)
    }
}