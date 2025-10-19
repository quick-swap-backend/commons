package quickswap.commons.adapter.shared.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.id.UserId

data class CustomUserDetails(
  val userId: UserId,
  val email: Email
) : UserDetails {

  override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()

  override fun getPassword(): String = ""

  override fun getUsername(): String = userId.value

  override fun isAccountNonExpired(): Boolean = true

  override fun isAccountNonLocked(): Boolean = true

  override fun isCredentialsNonExpired(): Boolean = true

  override fun isEnabled(): Boolean = true
}