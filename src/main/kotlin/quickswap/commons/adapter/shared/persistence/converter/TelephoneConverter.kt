package quickswap.commons.adapter.shared.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import quickswap.commons.domain.shared.vo.Telephone

@Converter(autoApply = true)
class TelephoneConverter : AttributeConverter<Telephone, String> {

  override fun convertToDatabaseColumn(telephone: Telephone?): String? {
    return telephone?.value
  }

  override fun convertToEntityAttribute(value: String?): Telephone? {
    return value?.let { Telephone(it) }
  }
}