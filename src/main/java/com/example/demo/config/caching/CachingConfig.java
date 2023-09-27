package com.example.demo.config.caching;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
public class CachingConfig {

  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(600)); // Cache entries for 10 minutes

    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(cacheConfiguration)
        .build();
  }

//  @Bean
//  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//    return (builder) -> builder
//        .withCacheConfiguration("usersList",
//            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));
//  }

  @Primary
  @Bean
  public CacheManager alternateCacheManager() {
    return new ConcurrentMapCacheManager("customerOrders", "orderprice");
  }
}