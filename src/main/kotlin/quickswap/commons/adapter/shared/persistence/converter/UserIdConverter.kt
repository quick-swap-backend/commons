package quickswap.commons.adapter.shared.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import quickswap.commons.domain.shared.id.UserId

@Converter(autoApply = true)
class UserIdConverter : AttributeConverter<UserId, String> {

  override fun convertToDatabaseColumn(userId: UserId?): String? {
    return userId?.value
  }

  override fun convertToEntityAttribute(value: String?): UserId? {
    return value?.let { UserId(it) }
  }
}