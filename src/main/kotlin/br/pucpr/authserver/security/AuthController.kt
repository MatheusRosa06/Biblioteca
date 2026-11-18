package br.pucpr.authserver.security

import br.pucpr.authserver.users.UserRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class LoginRequest(
    @field:Email @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String
)

data class LoginResponse(val token: String)

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val user = userRepository.findByEmail(request.email)
        if (user == null || !passwordEncoder.matches(request.password, user.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        return ResponseEntity.ok(LoginResponse(jwtService.generateToken(user.email)))
    }
}
