package com.ed.payment.infrastructure.out.mq.config;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static io.confluent.kafka.serializers.KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class KafkaConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.consumer.group-id}")
  private String consumerGroupId;

  @Value("${spring.kafka.schema-registry-url}")
  private String schemaRegistryUrl;

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(GROUP_ID_CONFIG, consumerGroupId);
    configProps.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    configProps.put(VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
    configProps.put(SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
    configProps.put(SPECIFIC_AVRO_READER_CONFIG, true);
    return new DefaultKafkaConsumerFactory<>(configProps);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}
