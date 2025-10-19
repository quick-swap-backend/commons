package quickswap.commons.domain.shared

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import quickswap.commons.domain.shared.vo.Email
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class EmailTest {

  @Test
  fun validate() {
    assertThrows<IllegalArgumentException> { Email("a@b.c") }

    Email("a@b.co")
  }

  @Test
  fun equals() {
    assertEquals(
      Email("a@b.co"),
      Email("a@b.co")
    )

    assertNotEquals(
      Email("a@b.com"),
      Email("a@b.co")
    )
  }
}