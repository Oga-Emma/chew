package app.seven.chew.auth.exception


class EmailInUseException(message: String = "Email is already in use"): RuntimeException(message)