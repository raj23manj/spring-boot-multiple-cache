package com.example.demo.controller;

import com.example.demo.service.ExampleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
  public List<String> processRedisListTemplate() {
    return exampleService.processRedisListData();
  }

  @GetMapping("/process/redis-list-recreate")
  public String processRecreateRedisList() throws JsonProcessingException {
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

  /////////////////

  @GetMapping("/string/get/{input}")
  public String stringGet(@PathVariable String input) {
    return exampleService.stringById(input);
  }

  @GetMapping("/string/save/{input}")
  public String stringSave(@PathVariable String input) {
    return exampleService.saveString(input);
  }

  @GetMapping("/string/getlist")
  public Map<String, String> stringGetList() {
    return exampleService.getAllString();
  }

  @GetMapping("/string/delete/{input}")
  public String stringDelete(@PathVariable String input) {
     exampleService.deleteString(input);
     return "ok";
  }

}
