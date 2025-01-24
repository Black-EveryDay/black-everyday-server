package com.ed.orderservice.infrastructure.message.kafka.config;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.OrderPaymentCreateRequestEvent;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.schema-registry-url}")
  private String schemaRegistryUrl;

  @Bean
  public ProducerFactory<String, OrderPaymentCreateRequestEvent> producerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    configProps.put(SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public ProducerFactory<String, OrderPaymentCancelRequestEvent> orderPaymentCancelProducerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    configProps.put(SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, OrderPaymentCreateRequestEvent> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
  @Bean
  public KafkaTemplate<String, OrderPaymentCancelRequestEvent> orderPaymentCancelKafkaTemplate() {
    return new KafkaTemplate<>(orderPaymentCancelProducerFactory());
  }
}
