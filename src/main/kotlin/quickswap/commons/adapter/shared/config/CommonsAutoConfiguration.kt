package quickswap.commons.adapter.shared.config

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetailsService
import quickswap.commons.adapter.shared.security.CustomUserDetailsService
import quickswap.commons.adapter.shared.security.JwtAuthenticationFilter
import quickswap.commons.adapter.shared.security.SecurityAuthenticationContext
import quickswap.commons.application.shared.ports.out.AuthenticationContext
import quickswap.commons.application.shared.ports.out.TokenResolver

@AutoConfiguration
class CommonsAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(AuthenticationContext::class)
  fun authenticationContext(): AuthenticationContext {
    return SecurityAuthenticationContext()
  }

  /* 사용하는 모듈에서 AbstractJwtTokenResolver 를 구현할 경우에만 주입 */
  @Bean
  @ConditionalOnMissingBean(JwtAuthenticationFilter::class)
  @ConditionalOnBean(TokenResolver::class)
  fun jwtAuthenticationFilter(tokenResolver: TokenResolver): JwtAuthenticationFilter {
    return JwtAuthenticationFilter(tokenResolver)
  }

  @Bean
  fun customUserDetailService(): UserDetailsService {
    return CustomUserDetailsService()
  }
}