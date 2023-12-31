package app.seven.chew.auth

import app.seven.chew.auth.business.AuthBusiness
import app.seven.chew.auth.model.AuthResponse
import app.seven.chew.auth.model.LoginRequest
import app.seven.chew.auth.model.SignupRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Endpoints for authenticating users")
class AuthController(val authBusiness: AuthBusiness) {

    @Operation(summary = "Login with email and password")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Authenticate user email and password, returns accessToken", content = [
            (Content(mediaType = "application/json", array = (
                    ArraySchema(schema = Schema(implementation = AuthResponse::class)))))
        ]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
    )
    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<*> {
        return ResponseEntity.ok(authBusiness.login(request))
    }

    @Operation(summary = "Create new account")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Create a new user account, returns accessToken", content = [
            (Content(mediaType = "application/json", array = (
                    ArraySchema(schema = Schema(implementation = AuthResponse::class)))))
        ]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
    )
    @PostMapping("/register")
    fun signup(@RequestBody @Valid request: SignupRequest): ResponseEntity<*> {
        return ResponseEntity.ok("")
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(): ResponseEntity<*> {
        return ResponseEntity.ok("")
    }

    @PostMapping("/change-password")
    fun changePassword(): ResponseEntity<*> {
        return ResponseEntity.ok("")
    }
}