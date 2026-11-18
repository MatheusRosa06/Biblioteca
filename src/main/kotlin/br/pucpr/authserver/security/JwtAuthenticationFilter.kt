package br.pucpr.authserver.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        val token = header?.takeIf { it.startsWith("Bearer ") }?.substring(7)
        val email = token?.let { jwtService.validateToken(it) }

        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            val authentication = UsernamePasswordAuthenticationToken(
                email,
                null,
                listOf(SimpleGrantedAuthority("ROLE_AUTHENTICATED"))
            )
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}
