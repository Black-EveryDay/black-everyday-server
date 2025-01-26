package com.ed.productservice.libs.config;

import com.ed.productservice.domain.vo.ProductDetails;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {
  @Bean
  public RedisTemplate<String, ProductDetails> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, ProductDetails> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    template.setKeySerializer(new StringRedisSerializer());

    Jackson2JsonRedisSerializer<ProductDetails> serializer =
        new Jackson2JsonRedisSerializer<>(ProductDetails.class);
    template.setValueSerializer(serializer);

    return template;
  }

  @Bean
  public ValueOperations<String, ProductDetails> valueOperations(RedisTemplate<String, ProductDetails> redisTemplate) {
    return redisTemplate.opsForValue();
  }
}