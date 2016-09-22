package yd.model;

import yd.model.httpbin.GetResponse;

import java.util.Base64;

public class MyResult {
  private final String base64;
  private final String text;
  private final GetResponse json;

  public MyResult(byte[] raw, String text, GetResponse json) {
    this.base64 = Base64.getEncoder().encodeToString(raw);
    this.text = text;
    this.json = json;
  }

  public String getBase64() {
    return base64;
  }

  public String getText() {
    return text;
  }

  public GetResponse getJson() {
    return json;
  }
}
