package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExampleService {

  private RedisCacheService redisCacheService;
  @Autowired
  public ExampleService(RedisCacheService redisCacheService) {
    this.redisCacheService = redisCacheService;
  }

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

  @Cacheable(value = "exampleRedisCacheList", cacheManager = "redisCacheManager")
  public List<String> processRedisList() {
    List<String> data = new ArrayList<>();
    data.add("babu");
    data.add("night");
    // Simulate time-consuming computation
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return data;
  }

  public String processRedisListData() {
    return redisCacheService.getFromCache("exampleRedisCacheList");
  }

  public void processRecreateRedisList() {
    List<String> data = new ArrayList<>();
    data.add("hello");
    data.add("good-morning");
    data.add("fine");
    // re-populate redis cache
    redisCacheService.evictCache("exampleRedisCacheList");
    redisCacheService.addToCache("exampleRedisCacheList", data,  10000);
  }

  @CacheEvict(value = "customerOrders", allEntries = true)
  public void emptyExampleCache() {

  }
// not working
//  @CacheEvict(value = "exampleRedisCache", allEntries = true)
//  public void emptyExampleRedisCache() {
//
//  }

  public void emptyExampleRedisCache() {
    redisCacheService.evictCache("exampleRedisCache::hello-redis");
  }
}
