package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  public void addToCache(String key, String value, long timeoutInSeconds) {
    redisTemplate.opsForValue().set(key, value, timeoutInSeconds, TimeUnit.SECONDS);
  }

  public void addToCache(String key, List<String> value, long timeoutInSeconds) {
    redisTemplate.opsForValue().set(key, value.toString(), timeoutInSeconds, TimeUnit.SECONDS);
  }

  public String getFromCache(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void evictCache(String key) {
    redisTemplate.delete(key);
  }
}
