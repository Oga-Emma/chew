package app.seven.chew.auth.web

import app.seven.chew.auth.business.AuthBusiness
import app.seven.chew.auth.model.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Endpoints for authenticating users")
class AuthController(val authBusiness: AuthBusiness) {

//    @Hidden
//    @PostMapping("/jwt/validate")
//    fun validate(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
//        val userId = userDetails.username
//        val roles = userDetails.authorities.map { it.authority }
//
//        return ResponseEntity.ok(
//            AuthenticatedRequestResponse(id = userId, roles = roles)
//        )
//    }
//
//    @Operation(summary = "Login with email and password")
//    @ApiResponses(
//        value = [
//            ApiResponse(
//                responseCode = "200",
//                description = "Authenticate user email and password, returns accessToken",
//                content = [
//                    (Content(
//                        mediaType = "application/json", array = (
//                                ArraySchema(schema = Schema(implementation = AuthResponse::class)))
//                    ))
//                ]
//            ),
//            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
//    )
//    @PostMapping("/login")
//    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<*> {
//        return ResponseEntity.ok(authBusiness.login(request))
//    }

    @Operation(summary = "Create new account")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Create a new user account, returns user and session information", content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = AuthResponse::class)))
                    ))
                ]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
    )
    @PostMapping("/register")
    fun signup(@RequestBody @Valid request: SignupRequest): ResponseEntity<*> {
        return ResponseEntity.ok(authBusiness.createAccount(request))
    }

//
//    @PostMapping("/request-password-reset")
//    fun requestPasswordReset(@RequestBody @Valid request: ResetPasswordRequest): ResponseEntity<*> {
//        return ResponseEntity.ok(authBusiness.requestPasswordReset(request))
//    }
//
//    @PostMapping("/complete-password-reset")
//    fun completePasswordReset(@RequestBody @Valid request: CompletePasswordResetRequest): ResponseEntity<*> {
//        return ResponseEntity.ok(authBusiness.completePasswordReset(request))
//    }
//
//    @PostMapping("/{userId}/refresh-access-token")
//    fun refreshAccessToken(
//        @PathVariable userId: UUID,
//        @RequestBody @Valid request: RefreshAccessTokenRequest
//    ): ResponseEntity<*> {
//        return ResponseEntity.ok(authBusiness.refreshAccessToken(userId, request))
//    }
//
//    @PostMapping("/change-password")
//    fun changePassword(
//        principal: Principal,
//        @RequestBody @Valid request: ChangePasswordRequest
//    ): ResponseEntity<*> {
//        return ResponseEntity.ok(authBusiness.changePassword(UUID.fromString(principal.name), request))
//    }
}