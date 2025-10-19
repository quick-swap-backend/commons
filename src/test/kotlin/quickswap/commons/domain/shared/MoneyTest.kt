package quickswap.commons.domain.shared

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import quickswap.commons.domain.shared.vo.Money
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class MoneyTest {

  @Test
  fun `금액이 음수일 경우 예외 발생`() {
    // given & when & then
    assertThrows<IllegalArgumentException> {
      Money(BigDecimal("-100"))
    }
  }

  @Test
  fun `금액이 0이면 정상 생성`() {
    // given & when
    val money = Money(BigDecimal.ZERO)

    // then
    assertEquals(BigDecimal.ZERO, money.amount)
  }

  @Test
  fun `of 팩토리 메서드로 Long 타입 금액 생성`() {
    // given & when
    val money = Money.of(1000L)

    // then
    assertEquals(BigDecimal("1000"), money.amount)
  }

  @Test
  fun `of 팩토리 메서드로 String 타입 금액 생성`() {
    // given & when
    val money = Money.of("1000.50")

    // then
    assertEquals(BigDecimal("1000.50"), money.amount)
  }

  @Test
  fun `ZERO 상수 확인`() {
    // given & when
    val zero = Money.ZERO

    // then
    assertTrue(zero.isZero())
    assertEquals(BigDecimal.ZERO, zero.amount)
  }

  @Test
  fun `두 금액 더하기`() {
    // given
    val money1 = Money.of(1000L)
    val money2 = Money.of(500L)

    // when
    val result = money1.add(money2)

    // then
    assertEquals(BigDecimal("1500.00"), result.amount)
  }

  @Test
  fun `두 금액 빼기`() {
    // given
    val money1 = Money.of(1000L)
    val money2 = Money.of(300L)

    // when
    val result = money1.subtract(money2)

    // then
    assertEquals(BigDecimal("700.00"), result.amount)
  }

  @Test
  fun `빼기 결과가 음수면 예외 발생`() {
    // given
    val money1 = Money.of(100L)
    val money2 = Money.of(200L)

    // when & then
    assertThrows<IllegalArgumentException> {
      money1.subtract(money2)
    }
  }

  @Test
  fun `정수(Int)와 곱하기`() {
    // given
    val money = Money.of(1000L)

    // when
    val result = money.multiply(3)

    // then
    assertEquals(BigDecimal("3000.00"), result.amount)
  }

  @Test
  fun `정수(Long)와 곱하기`() {
    // given
    val money = Money.of(1500L)

    // when
    val result = money.multiply(2L)

    // then
    assertEquals(BigDecimal("3000.00"), result.amount)
  }

  @Test
  fun `BigDecimal과 곱하기`() {
    // given
    val money = Money.of(1000L)

    // when
    val result = money.multiply(BigDecimal("1.5"))

    // then
    assertEquals(BigDecimal("1500.00"), result.amount)
  }

  @Test
  fun `Money와 곱하기`() {
    // given
    val money1 = Money.of(100L)
    val money2 = Money.of(3L)

    // when
    val result = money1.multiply(money2)

    // then
    assertEquals(BigDecimal("300.00"), result.amount)
  }

  @Test
  fun `정수(Int)로 나누기`() {
    // given
    val money = Money.of(1000L)

    // when
    val result = money.divide(3)

    // then
    assertEquals(BigDecimal("333.33"), result.amount)  // HALF_UP 반올림
  }

  @Test
  fun `정수(Long)로 나누기`() {
    // given
    val money = Money.of(1000L)

    // when
    val result = money.divide(4L)

    // then
    assertEquals(BigDecimal("250.00"), result.amount)
  }

  @Test
  fun `0으로 나누면 예외 발생 - Int`() {
    // given
    val money = Money.of(1000L)

    // when & then
    assertThrows<IllegalArgumentException> {
      money.divide(0)
    }
  }

  @Test
  fun `0으로 나누면 예외 발생 - Long`() {
    // given
    val money = Money.of(1000L)

    // when & then
    assertThrows<IllegalArgumentException> {
      money.divide(0L)
    }
  }

  @Test
  fun `금액 비교 - 크다`() {
    // given
    val money1 = Money.of(1000L)
    val money2 = Money.of(500L)

    // when & then
    assertTrue(money1 > money2)
    assertTrue(money1.isGreaterThan(money2))
  }

  @Test
  fun `금액 비교 - 작다`() {
    // given
    val money1 = Money.of(500L)
    val money2 = Money.of(1000L)

    // when & then
    assertTrue(money1 < money2)
    assertTrue(money1.isLessThan(money2))
  }

  @Test
  fun `금액 비교 - 크거나 같다`() {
    // given
    val money1 = Money.of(1000L)
    val money2 = Money.of(1000L)
    val money3 = Money.of(500L)

    // when & then
    assertTrue(money1 >= money2)
    assertTrue(money1 >= money3)
    assertTrue(money1.isGreaterThanOrEqual(money2))
    assertTrue(money1.isGreaterThanOrEqual(money3))
  }

  @Test
  fun `금액 비교 - 작거나 같다`() {
    // given
    val money1 = Money.of(500L)
    val money2 = Money.of(1000L)
    val money3 = Money.of(500L)

    // when & then
    assertTrue(money1 <= money2)
    assertTrue(money1 <= money3)
    assertTrue(money1.isLessThanOrEqual(money2))
    assertTrue(money1.isLessThanOrEqual(money3))
  }

  @Test
  fun `compareTo 메서드 테스트`() {
    // given
    val money1 = Money.of(1000L)
    val money2 = Money.of(1000L)
    val money3 = Money.of(500L)

    // when & then
    assertEquals(0, money1.compareTo(money2))
    assertTrue(money1.compareTo(money3) > 0)
    assertTrue(money3.compareTo(money1) < 0)
  }

  @Test
  fun `equals - scale이 다르지만 값이 같으면 동일`() {
    // given
    val money1 = Money(BigDecimal("100.00"))
    val money2 = Money(BigDecimal("100.0"))

    // when & then
    assertEquals(money1, money2)
  }

  @Test
  fun `equals - 값이 다르면 다름`() {
    // given
    val money1 = Money.of(1000L)
    val money2 = Money.of(2000L)

    // when & then
    assertNotEquals(money1, money2)
  }

  @Test
  fun `hashCode - scale이 다르지만 값이 같으면 동일`() {
    // given
    val money1 = Money(BigDecimal("100.00"))
    val money2 = Money(BigDecimal("100.0"))

    // when & then
    assertEquals(money1.hashCode(), money2.hashCode())
  }

  @Test
  fun `isZero - 0이면 true`() {
    // given
    val money = Money.ZERO

    // when & then
    assertTrue(money.isZero())
  }

  @Test
  fun `isZero - 0이 아니면 false`() {
    // given
    val money = Money.of(100L)

    // when & then
    assertFalse(money.isZero())
  }

  @Test
  fun `isPositive - 양수면 true`() {
    // given
    val money = Money.of(100L)

    // when & then
    assertTrue(money.isPositive())
  }

  @Test
  fun `isPositive - 0이면 false`() {
    // given
    val money = Money.ZERO

    // when & then
    assertFalse(money.isPositive())
  }

  @Test
  fun `toString 형식 확인`() {
    // given
    val money = Money.of(1000L)

    // when
    val result = money.toString()

    // then
    assertEquals("1000.00원", result)
  }

  @Test
  fun `소수점 반올림 확인 - HALF_UP`() {
    // given
    val money = Money.of(1000L)

    // when
    val result = money.divide(3)  // 333.333...

    // then
    assertEquals(BigDecimal("333.33"), result.amount)  // 반올림
  }

  @Test
  fun `소수점 반올림 확인 - HALF_UP (올림)`() {
    // given
    val money1 = Money(BigDecimal("10.555"))
    val money2 = Money(BigDecimal("1"))

    // when
    val result = money1.add(money2)

    // then
    assertEquals(BigDecimal("11.56"), result.amount)  // 0.555 -> 0.56 (반올림)
  }

  @Test
  fun `연속된 연산 체이닝`() {
    // given
    val price = Money.of(1000L)
    val quantity = 3
    val discount = Money.of(500L)

    // when
    val result = price
      .multiply(quantity)  // 3000
      .subtract(discount)  // 2500
      .divide(2)           // 1250

    // then
    assertEquals(BigDecimal("1250.00"), result.amount)
  }

  @Test
  fun `Set에서 중복 제거 확인 - equals와 hashCode 일관성`() {
    // given
    val money1 = Money(BigDecimal("100.00"))
    val money2 = Money(BigDecimal("100.0"))
    val money3 = Money.of(200L)

    // when
    val set = setOf(money1, money2, money3)

    // then
    assertEquals(2, set.size)  // money1과 money2는 같은 것으로 간주
  }

  @Test
  fun `BigDecimal 생성 방식에 따른 차이 없음`() {
    // given
    val money1 = Money(BigDecimal("1000"))
    val money2 = Money(BigDecimal.valueOf(1000))
    val money3 = Money.of(1000L)

    // when & then
    assertEquals(money1, money2)
    assertEquals(money2, money3)
  }
}