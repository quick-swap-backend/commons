package quickswap.commons.domain.shared

fun interface IdProvider {
  fun provide():String
}