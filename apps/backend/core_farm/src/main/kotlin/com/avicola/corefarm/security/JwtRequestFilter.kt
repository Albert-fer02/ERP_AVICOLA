// JwtRequestFilter.kt - Filter for JWT validation
// Justification: Intercepts requests to validate JWT tokens.
// Sets authentication context for secured endpoints.
// Handles unauthorized access gracefully.

package com.avicola.corefarm.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtRequestFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        println("DEBUG: Authorization header: $authorizationHeader")  // Added debug log

        var username: String? = null
        var jwt: String? = null

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            username = jwtUtil.getUsernameFromToken(jwt)
            println("DEBUG: Extracted username: $username")  // Added debug log
        } else {
            println("DEBUG: No valid Authorization header found")  // Added debug log
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            if (jwtUtil.validateToken(jwt!!)) {
                val authenticationToken = UsernamePasswordAuthenticationToken(
                    username, null, emptyList()
                )
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }

        chain.doFilter(request, response)
    }
}