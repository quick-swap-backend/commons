package quickswap.commons.domain.shared.exception

import java.lang.RuntimeException

class DuplicateEmailException(
  message: String = "이미 사용 중인 이메일입니다"
): RuntimeException(message)