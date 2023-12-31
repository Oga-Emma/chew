package app.seven.chew.auth.config

import app.seven.chew.auth.exception.InvalidCredentialException
import app.seven.chew.auth.model.dto.ApiResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.convert.ConversionFailedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors


@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(ConversionFailedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConversion(ex: RuntimeException): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity(
            ApiResponse.Error(
                message = "Unknown error",
                error = ex.message
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(InvalidCredentialException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidCredential(ex: InvalidCredentialException): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity(
            ApiResponse.Error(message = "Invalid credential", error = ex.message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity(
            ApiResponse.Error(message = "Invalid request body", error = ex.message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<*>> {
        val errorMap = ex.allErrors
            .stream().map { it.defaultMessage }.collect(Collectors.toList()).joinToString(", ")

        return ResponseEntity(ApiResponse.Error(message = errorMap, error = ex.message), HttpStatus.BAD_REQUEST)
    }

    /*@ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBookNotFound(ex: RuntimeException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }*/
}