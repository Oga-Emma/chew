package app.seven.chew.user.model

sealed class ApiResponse<T> (
    val success: Boolean,
    val message: String,
    val error: String?,
    val data: T?
) {
    class Error(message: String, error: String?): ApiResponse<Unit>(
        success = false,
        message = message,
        error = error,
        data = null
    )
    class Success<T>(message: String, data: T): ApiResponse<T>(
        success = true,
        message = message,
        error = null,
        data = data
    )
}