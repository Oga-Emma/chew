package app.seven.chew.exception


class EmailInUseException(message: String = "Email is already in use"): RuntimeException(message)