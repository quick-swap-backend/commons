package quickswap.commons.domain.shared.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import quickswap.commons.domain.shared.IdProvider
import java.io.Serializable

@Embeddable
data class UserId(@Column(name = "id") val value: String) : Serializable {
  constructor(provider: IdProvider) : this(provider.provide())
}