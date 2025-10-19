package quickswap.commons.adapter.shared.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import quickswap.commons.domain.shared.vo.Email

@Converter(autoApply = true)
class EmailConverter : AttributeConverter<Email, String> {

  override fun convertToDatabaseColumn(email: Email?): String? {
    return email?.value
  }

  override fun convertToEntityAttribute(value: String?): Email? {
    return value?.let { Email(it) }
  }
}