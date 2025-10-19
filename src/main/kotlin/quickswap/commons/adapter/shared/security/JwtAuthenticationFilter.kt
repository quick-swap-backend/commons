package quickswap.commons.adapter.shared.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import quickswap.commons.application.shared.ports.out.TokenResolver

class JwtAuthenticationFilter(
  private val tokenResolver: TokenResolver
) : OncePerRequestFilter() {

  private val logger = LoggerFactory.getLogger(javaClass)

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    try {
      val token = extractToken(request)

      if (token != null && tokenResolver.validateToken(token)) {
        val userId = tokenResolver.getUserIdFromToken(token)
        val email = tokenResolver.getEmailFromToken(token)

        val userDetails = CustomUserDetails(
          userId = userId,
          email = email
        )

        val authentication = UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.authorities
        )

        SecurityContextHolder.getContext().authentication = authentication
      }
    } catch (e: Exception) {
      logger.error("JWT authentication failed", e)
    }

    filterChain.doFilter(request, response)
  }

  private fun extractToken(request: HttpServletRequest): String? {
    val bearerToken = request.getHeader("Authorization")
    return if (bearerToken?.startsWith("Bearer ") == true) {
      bearerToken.substring(7)
    } else {
      null
    }
  }
}