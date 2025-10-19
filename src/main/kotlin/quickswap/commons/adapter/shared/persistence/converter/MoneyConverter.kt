package quickswap.commons.adapter.shared.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import quickswap.commons.domain.shared.vo.Money
import java.math.BigDecimal
import kotlin.let

@Converter(autoApply = true)
class MoneyConverter : AttributeConverter<Money, BigDecimal> {

  override fun convertToDatabaseColumn(money: Money?): BigDecimal? {
    return money?.amount
  }

  override fun convertToEntityAttribute(amount: BigDecimal?): Money? {
    return amount?.let { Money(it) }
  }
}