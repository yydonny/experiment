package yd.service;

import org.springframework.stereotype.Component;

@Component
public class CountingService {
  public int increment(int val) {
    return val + 3;
  }
}
