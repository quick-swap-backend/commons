package quickswap.commons.adapter.shared.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

abstract class AbstractKafkaConfig {

  protected abstract fun getBootstrapServers(): String
  protected abstract fun getGroupId(): String

  @Bean
  open fun producerFactory(): ProducerFactory<String, String> {
    val config = mapOf(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to getBootstrapServers(),
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
    )
    return DefaultKafkaProducerFactory(config)
  }

  @Bean
  open fun kafkaTemplate(): KafkaTemplate<String, String> {
    return KafkaTemplate(producerFactory())
  }

  @Bean
  open fun consumerFactory(): ConsumerFactory<String, String> {
    val config = mapOf(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to getBootstrapServers(),
      ConsumerConfig.GROUP_ID_CONFIG to getGroupId(),
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
    )
    return DefaultKafkaConsumerFactory(config)
  }

  @Bean
  open fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
    return ConcurrentKafkaListenerContainerFactory<String, String>().apply {
      consumerFactory = consumerFactory()
    }
  }
}