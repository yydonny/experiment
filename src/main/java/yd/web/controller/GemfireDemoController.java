package yd.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yd.service.GemfireClient;

@RestController
@RequestMapping("/gf")
public class GemfireDemoController {
  private final GemfireClient gemfireClient;

  @Autowired
  public GemfireDemoController(GemfireClient gemfireClient) {
    this.gemfireClient = gemfireClient;
  }
//
//  @RequestMapping
//  @ResponseBody
//  public Map<String, Set<String>> index() {
//    return Collections.singletonMap("keys", gemfireClient.keys());
//  }
//
//  @RequestMapping("/put")
//  public void put(@RequestParam String key, @RequestParam String value) {
//    gemfireClient.put(key, value);
//  }
//
//  @RequestMapping("/{key}")
//  @ResponseBody
//  public String get(@PathVariable String key) {
//    return gemfireClient.get(key);
//  }
}
