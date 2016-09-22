package yd.service;

import org.springframework.stereotype.Component;

@Component
public class GemfireClient {

//  private final Region<String, String> region2;
//
//  public GemfireClient() {
//    region2 = new ClientCacheFactory()
//        .addPoolLocator("winterfell", 55221) // the locator, not any server or peer
//        .setPoolSubscriptionEnabled(true)
//        .setPoolSubscriptionRedundancy(1)
//        .set("log-level", "warn")
//        .create()
//        .<String, String>createClientRegionFactory(ClientRegionShortcut.PROXY)
//        .create("region2");
//  }
//
//  public Set<String> keys() { return region2.keySetOnServer(); }
//
//  public void put(String key, String value) {
//    region2.put(key, value);
//  }
//
//  public String get(String key) {
//    return region2.get(key);
//  }
}
