package quickswap.commons.adapter.shared.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import quickswap.commons.application.shared.ports.out.AuthenticationContext
import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Email

@Component
class SecurityAuthenticationContext : AuthenticationContext {

  override fun getCurrentUserId(): UserId {
    return getUserDetails().userId
  }

  override fun getCurrentEmail(): Email {
    return getUserDetails().email
  }

  override fun isAuthenticated(): Boolean {
    val auth = SecurityContextHolder.getContext().authentication
    return auth != null && auth.isAuthenticated
  }

  private fun getUserDetails(): CustomUserDetails {
    val authentication = SecurityContextHolder.getContext().authentication
      ?: throw UnauthorizedException("인증되지 않은 사용자입니다")

    return authentication.principal as? CustomUserDetails
      ?: throw UnauthorizedException("잘못된 인증 정보입니다")
  }
}