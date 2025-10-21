package quickswap.commons.adapter.shared.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import quickswap.commons.domain.shared.LogActions.FAIL
import quickswap.commons.domain.shared.LogActions.TRY
import quickswap.commons.domain.shared.LogHelper.format

@Component
class KafkaEventHandlerAdvice {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun <T> run(block: () -> T): T {
    return try {
      block()
    } catch (e: Exception) {
      logger.error(format(FAIL, "kafkaEventHandling", "execute"), e)
      throw e
    }
  }
}

class KafkaEventHandler(
  @PublishedApi internal val advice: KafkaEventHandlerAdvice,
  @PublishedApi internal val objectMapper: ObjectMapper,
) {
  @PublishedApi
  internal val logger: Logger = LoggerFactory.getLogger(javaClass)

  inline fun <reified T> handle(message: String, crossinline handler: (T) -> Unit) {
    logger.info(format(TRY, "kafkaEvent", "received", "message" to message))

    advice.run {
      val event = extractEvent<T>(message)
      logger.info(format(TRY, "kafkaEvent", "extracted", "eventType" to (T::class.simpleName ?: "Unknown")))
      handler(event)
    }
  }

  @PublishedApi
  internal inline fun <reified T> extractEvent(message: String): T {
    val rootNode = objectMapper.readTree(message)

    val eventJson = if (rootNode.has("payload")) {
      val payloadNode = rootNode.get("payload")
      if (payloadNode.isTextual) {
        payloadNode.asText()
      } else {
        objectMapper.writeValueAsString(payloadNode)
      }
    } else {
      message
    }

    return objectMapper.readValue(eventJson, T::class.java)
  }
}

@Configuration
class KafkaEventHandlerConfig {
  @Bean
  fun kafkaEventHandler(
    advice: KafkaEventHandlerAdvice,
    objectMapper: ObjectMapper
  ): KafkaEventHandler {
    return KafkaEventHandler(advice, objectMapper)
  }
}