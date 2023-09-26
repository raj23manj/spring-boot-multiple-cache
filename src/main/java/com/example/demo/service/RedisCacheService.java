package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  public void addToCache(String key, String value, long timeoutInSeconds) {
    redisTemplate.opsForValue().set(key, value, timeoutInSeconds, TimeUnit.SECONDS);
  }

  public void addToCache(String key, List<String> value, long timeoutInSeconds) throws JsonProcessingException {
    String jsonValue = objectMapper.writeValueAsString(value);
    redisTemplate.opsForValue().set(key, jsonValue, timeoutInSeconds, TimeUnit.SECONDS);
  }

  public String getFromCache(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public List<String> getListFromRedis(String key) {
    String jsonValue = redisTemplate.opsForValue().get(key);
    if (jsonValue != null) {
      try {
        return objectMapper.readValue(jsonValue, List.class);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Failed to deserialize JSON to list: " + e.getMessage());
      }
    }
    return null;
  }

  public void evictCache(String key) {
    redisTemplate.delete(key);
  }
}
