package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExampleService {

  private Map<String, String> db;

  private RedisCacheService redisCacheService;

  private RedisCacheManager redisCacheManager;
  @Autowired
  public ExampleService(RedisCacheService redisCacheService,
                        RedisCacheManager redisCacheManager) {

    this.redisCacheService = redisCacheService;
    this.db = new HashMap<>();
    this.redisCacheManager = redisCacheManager;
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

  // sets key differently
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

  public List<String> processRedisListData() {
    return redisCacheService.getListFromRedis("exampleRedisCacheList");
  }

  public void processRecreateRedisList() throws JsonProcessingException {
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

//////////////////////////////
  // https://www.mindbowser.com/spring-boot-with-redis-cache-using-annotation/
  @Cacheable(value = "user", key = "#name", cacheManager = "redisCacheManager")
  public String stringById(String name) {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return this.db.get(name);
  }

  @Caching(evict = { @CacheEvict(value = "usersList", allEntries = true, cacheManager = "redisCacheManager"), }, put = {
      @CachePut(value = "user", key = "#name", cacheManager = "redisCacheManager") })
  public String saveString(String name) {
    this.db.put(name, name);
    return name;
  }

  @Cacheable(value = "usersList", cacheManager = "redisCacheManager")
  public Map<String, String> getAllString() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return this.db;
  }

  @Caching(evict = { @CacheEvict(value = "usersList", allEntries = true, cacheManager = "redisCacheManager"),
      @CacheEvict(value = "user", key = "#name", cacheManager = "redisCacheManager"), })
  public void deleteString(String name) {
    this.db.remove(name);
  }

  // evict caches programmatically
  // https://www.baeldung.com/spring-boot-evict-cache
  public void evictAllCaches() {
    redisCacheManager.getCacheNames().stream()
        .forEach(cacheName -> redisCacheManager.getCache(cacheName).clear());
  }
}
