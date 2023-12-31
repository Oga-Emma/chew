package app.seven.chew.config

import app.seven.chew.features.auth.service.AuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
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
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // Define public and private routes
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers(HttpMethod.POST, "/api/auth/*").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/meals").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll() // In case you have a frontend
            }

        // Configure JWT
        http.oauth2ResourceServer { oauth -> oauth.jwt { } }

        http.authenticationManager { authManager ->
            val jwt = authManager as BearerTokenAuthenticationToken
            val user = authService.getUserFromToken(jwt.token) ?: throw InvalidBearerTokenException("Invalid token")
            UsernamePasswordAuthenticationToken(user, "", listOf(SimpleGrantedAuthority("USER")))
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
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}