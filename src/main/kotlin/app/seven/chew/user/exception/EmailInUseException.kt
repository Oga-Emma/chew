package app.seven.chew.user.exception


class EmailInUseException(message: String = "Email is already in use"): RuntimeException(message)