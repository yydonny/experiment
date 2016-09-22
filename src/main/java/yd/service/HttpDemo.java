package yd.service;

import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import yd.model.MyResult;
import yd.model.httpbin.GetResponse;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.springframework.http.HttpMethod.GET;

@Component
public class HttpDemo {
  private final ExecutorService executorService = Executors.newCachedThreadPool();
  private final Scheduler scheduler = Schedulers.from(executorService);
  private final RestTemplate restTemplate = new RestTemplate();

  public HttpDemo() {
    restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
  }

  private <T> Observable<T> wrap(Future<T> future) {
    return Observable.from(future, scheduler);
  }

  public Future<GetResponse> fetchJSON() {
    return executorService.submit(() ->
        restTemplate
            .exchange("http://httpbin.org/get", GET, null, GetResponse.class)
            .getBody());

  }

  private Future<byte[]> fetchBinary() {
    return executorService.submit(() -> {
//      final String url = "http://httpbin.org/bytes/128";
      final String url = "http://httpbin.org/status/403";
      return restTemplate.getForObject(url, byte[].class);
    });
  }

  private Future<String> fetchText() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
    return executorService.submit(() -> {
      ResponseEntity<byte[]> response = restTemplate.exchange(
          "http://httpbin.org/stream/2",
          HttpMethod.GET,
          new HttpEntity<String>(headers),
          byte[].class);
      return Charset.forName("UTF-8").decode(ByteBuffer.wrap(response.getBody())).toString();
    });
  }

  public Observable<MyResult> fetchAll() {
    return Observable.zip(wrap(fetchText()), wrap(fetchBinary()), wrap(fetchJSON()),
        (text, bin, json) -> new MyResult(bin, text, json));
  }
}
