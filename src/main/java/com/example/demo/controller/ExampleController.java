package com.example.demo.controller;

import com.example.demo.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ExampleController {

  @Autowired
  private ExampleService exampleService;

  @GetMapping("/process/{input}")
  public String processInput(@PathVariable String input) {
    return exampleService.process(input);
  }

  @GetMapping("/process/redis/{input}")
  public String processRedisInput(@PathVariable String input) {
    return exampleService.processRedis(input);
  }

  @GetMapping("/process/redis-list")
  public List<String> processRedisList() {
    return exampleService.processRedisList();
  }

  @GetMapping("/process/redis-list-template")
  public String processRedisListTemplate() {
    return exampleService.processRedisListData();
  }

  @GetMapping("/process/redis-list-recreate")
  public String processRecreateRedisList() {
    exampleService.processRecreateRedisList();
    return "ok";
  }

  @GetMapping("/process/redis-evict")
  public String processRedisEvict() {
    exampleService.emptyExampleRedisCache();
    return "ok";
  }

  @GetMapping("/process/evict")
  public String processEvict() {
     exampleService.emptyExampleCache();
    return "ok";
  }
}
