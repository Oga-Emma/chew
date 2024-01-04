package app.seven.chew.auth.utils

class Constants {
    companion object {
        const val USER_CREATED_QUEUE = "chew-user-created-queue"
        const val USER_UPDATED_QUEUE = "chew-user-updated-queue"
        const val CHEW_USER_CREATED_MESSAGE_TOPIC = "chew-user-created-topic"
        const val CHEW_USER_UPDATED_MESSAGE_TOPIC = "chew-user-updated-topic"
        const val CHEW_USER_CREATED_ROUTING_KEY = "chew.user.created"
        const val CHEW_USER_UPDATED_ROUTING_KEY = "chew.user.updated"
//        const val CHEW_TOPIC_ROUTING_KEY = "chew.user.#"

        const val ROLE_USER = "USER"
    }


}