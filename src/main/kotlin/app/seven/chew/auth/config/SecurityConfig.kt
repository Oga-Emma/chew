package app.seven.chew.auth.config

import app.seven.chew.auth.service.AuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authService: AuthService,
) {
    private val AUTH_WHITELIST = listOf(
        "/api/auth/login",
        "/api/auth/register",
        "/api/auth/request-password-reset",
        "/api/auth/complete-password-reset",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/api/public/**",
        "/api/public/authenticate",
        "/actuator/*",
        "/swagger-ui/**"
    )

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // Define public and private routes
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers(HttpMethod.POST, "/api/auth/change-password").authenticated()
                    .requestMatchers(HttpMethod.POST, "/api/auth/jwt/validate").authenticated()
                    .anyRequest().permitAll() // for frontend like swagger
            }

        // Configure JWT
        http.oauth2ResourceServer { oauth -> oauth.jwt { } }
        http.authenticationManager { authManager ->
            val jwt = authManager as BearerTokenAuthenticationToken
            val authUser = authService.getUserFromToken(jwt.token) ?: throw InvalidBearerTokenException("Invalid token")

            val role = authUser.user.role
            val authenticatedUser = User.builder()
                .username(authUser.user.email)
                .password(authUser.password)
                .roles(role)
                .build()

            val grantedRoles = listOf(SimpleGrantedAuthority(role.uppercase()))
            UsernamePasswordAuthenticationToken(authenticatedUser, "", grantedRoles)
        }

        // Other configuration
        http.cors {}
        http.sessionManagement { sessionManager ->
            sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.csrf { csrf -> csrf.disable() }
        http.headers { headers ->
            headers.frameOptions { options -> options.disable() }
            headers.xssProtection { pro -> pro.disable() }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        // allow localhost for dev purposes
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}