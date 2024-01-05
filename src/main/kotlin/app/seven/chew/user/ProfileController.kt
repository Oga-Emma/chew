package app.seven.chew.user

import app.seven.chew.user.business.UserProfileBusiness
import app.seven.chew.user.model.dto.UpdateUserProfileRequest
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
import java.util.UUID

@RestController
@RequestMapping("/api/profile")
@Tag(name = "User API", description = "Endpoints for user management")
class ProfileController(val userProfileBusiness: UserProfileBusiness) {

    @Operation(summary = "Login with email and password")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Update user profile, returns updated prfoile",
                content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = app.seven.chew.user.model.dto.ApiResponse::class)))
                    ))
                ]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
        ]
    )
    @PutMapping("{userId}/login")
    fun updateUserProfile(@RequestBody @Valid request: UpdateUserProfileRequest, @PathVariable userId: UUID): ResponseEntity<*> {
        return ResponseEntity.ok(userProfileBusiness.updateUserProfile(userId, request))
    }

//    @Operation(summary = "Create new account")
//    @ApiResponses(value = [
//        ApiResponse(responseCode = "200", description = "Create a new user account, returns accessToken", content = [
//            (Content(mediaType = "application/json", array = (
//                    ArraySchema(schema = Schema(implementation = AuthResponse::class)))))
//        ]),
//        ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
//    )
//    @PostMapping("/register")
//    fun signup(@RequestBody @Valid request: SignupRequest): ResponseEntity<*> {
//        return ResponseEntity.ok("")
//    }
//
//    @PostMapping("/forgot-password")
//    fun forgotPassword(): ResponseEntity<*> {
//        return ResponseEntity.ok("")
//    }
//
//    @PostMapping("/change-password")
//    fun changePassword(): ResponseEntity<*> {
//        return ResponseEntity.ok("")
//    }
}