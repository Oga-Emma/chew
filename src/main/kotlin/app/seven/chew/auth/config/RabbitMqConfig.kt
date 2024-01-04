package app.seven.chew.auth.config

import app.seven.chew.auth.service.RabbitMQEventListener
import app.seven.chew.auth.utils.Constants
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMqConfig {
    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun userCreatedQueue(@Value("\${app.rabbitmq.save-messages:false}") saveMessage: Boolean): Queue {
        return Queue(Constants.USER_CREATED_QUEUE, saveMessage)
    }

    @Bean
    fun userUpdatedQueue(@Value("\${app.rabbitmq.save-messages:false}") saveMessage: Boolean): Queue {
        return Queue(Constants.USER_UPDATED_QUEUE, saveMessage)
    }

    @Bean
    fun userCreatedExchange(): DirectExchange {
        return DirectExchange(Constants.CHEW_USER_CREATED_MESSAGE_TOPIC)
//        return TopicExchange(Constants.CHEW_MESSAGE_TOPIC)
    }

    @Bean
    fun userUpdatedExchange(): DirectExchange {
        return DirectExchange(Constants.CHEW_USER_UPDATED_MESSAGE_TOPIC)
    }

    /* @Bean
     fun binding(queue: Queue, exchange: TopicExchange): Binding {
         return BindingBuilder.bind(queue).to(exchange).with(Constants.CHEW_ROUTING_KEY)
     }*/

    @Bean
    fun userCreatedExchangeBinding(userCreatedQueue: Queue, userCreatedExchange: DirectExchange): Binding {
        return BindingBuilder.bind(userCreatedQueue).to(userCreatedExchange).with(Constants.CHEW_USER_CREATED_ROUTING_KEY)
    }

    @Bean
    fun userUpdatedExchangeBinding(userUpdatedQueue: Queue, userUpdatedExchange: DirectExchange): Binding {
        return BindingBuilder.bind(userUpdatedQueue).to(userUpdatedExchange).with(Constants.CHEW_USER_UPDATED_ROUTING_KEY)
    }

    @Bean
    fun container(
        connectionFactory: ConnectionFactory,
        listenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(Constants.USER_UPDATED_QUEUE)
        container.setMessageListener(listenerAdapter)
        return container
    }

    @Bean
    fun listenerAdapter(receiver: RabbitMQEventListener): MessageListenerAdapter {
        return MessageListenerAdapter(receiver, "receiveMessage")
    }
}