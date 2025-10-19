package quickswap.commons.application.shared.ports.out

import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Email

interface AuthenticationContext {
  fun getCurrentUserId(): UserId
  fun getCurrentEmail(): Email
  fun isAuthenticated(): Boolean
}