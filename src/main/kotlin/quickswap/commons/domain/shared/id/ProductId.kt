package quickswap.commons.domain.shared.id

import quickswap.commons.domain.shared.IdProvider
import java.io.Serializable

data class ProductId(val value: String) : Serializable {
  constructor(provider: IdProvider) : this(provider.provide())
}
