package quickswap.commons.domain.shared.vo

import java.math.BigDecimal
import java.math.RoundingMode

data class Money(val amount: BigDecimal) : Comparable<Money> {

  init {
    require(amount >= BigDecimal.ZERO) {
      "금액은 음수가 될 수 없습니다: $amount"
    }
  }

  companion object {
    val ZERO = Money(BigDecimal.ZERO)
    private const val SCALE = 2
    private val ROUNDING_MODE = RoundingMode.HALF_UP

    fun of(amount: Long): Money {
      return Money(BigDecimal.valueOf(amount))
    }

    fun of(amount: String): Money {
      return Money(BigDecimal(amount))
    }
  }

  fun add(other: Money): Money {
    return Money(amount.add(other.amount).setScale(SCALE, ROUNDING_MODE))
  }

  fun subtract(other: Money): Money {
    val result = amount.subtract(other.amount)
    return Money(result.setScale(SCALE, ROUNDING_MODE))
  }

  fun multiply(quantity: Long): Money {
    return Money(amount.multiply(BigDecimal.valueOf(quantity)).setScale(SCALE, ROUNDING_MODE))
  }

  fun multiply(quantity: Int): Money {
    return Money(amount.multiply(BigDecimal.valueOf(quantity.toLong())).setScale(SCALE, ROUNDING_MODE))
  }

  fun multiply(multiplier: BigDecimal): Money {
    return Money(amount.multiply(multiplier).setScale(SCALE, ROUNDING_MODE))
  }

  fun multiply(multiplier: Money): Money {
    return Money(amount.multiply(multiplier.amount).setScale(SCALE, ROUNDING_MODE))
  }

  fun divide(divisor: Long): Money {
    require(divisor != 0L) { "0으로 나눌 수 없습니다" }
    return Money(amount.divide(BigDecimal.valueOf(divisor), SCALE, ROUNDING_MODE))
  }

  fun divide(divisor: Int): Money {
    require(divisor != 0) { "0으로 나눌 수 없습니다" }
    return Money(amount.divide(BigDecimal.valueOf(divisor.toLong()), SCALE, ROUNDING_MODE))
  }

  override fun compareTo(other: Money): Int {
    return amount.compareTo(other.amount)
  }

  fun isGreaterThan(other: Money): Boolean = this > other
  fun isGreaterThanOrEqual(other: Money): Boolean = this >= other
  fun isLessThan(other: Money): Boolean = this < other
  fun isLessThanOrEqual(other: Money): Boolean = this <= other

  fun isZero(): Boolean = amount.compareTo(BigDecimal.ZERO) == 0
  fun isPositive(): Boolean = amount > BigDecimal.ZERO

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Money) return false
    return amount.compareTo(other.amount) == 0
  }

  override fun hashCode(): Int {
    return amount.stripTrailingZeros().hashCode()
  }

  override fun toString(): String {
    return "${amount.setScale(SCALE, ROUNDING_MODE)}원"
  }

}