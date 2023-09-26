package com.example.demo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

  @Cacheable(value = "customerOrders", key = "#input", cacheManager = "alternateCacheManager")
  public String process(String input) {
    // Simulate time-consuming computation
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "Processed: " + input;
  }

  @Cacheable(value = "exampleRedisCache", key = "#input", cacheManager = "redisCacheManager")
  public String processRedis(String input) {
    // Simulate time-consuming computation
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "RedisProcessed: " + input;
  }
}
