package quickswap.commons.adapter.shared.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import quickswap.commons.domain.shared.id.ProductId
import quickswap.commons.domain.shared.id.UserId

@Converter(autoApply = true)
class ProductIdConverter : AttributeConverter<ProductId, String> {

  override fun convertToDatabaseColumn(productId: ProductId?): String? {
    return productId?.value
  }

  override fun convertToEntityAttribute(value: String?): ProductId? {
    return value?.let { ProductId(it) }
  }
}