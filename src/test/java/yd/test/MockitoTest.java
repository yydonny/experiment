package yd.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import yd.model.httpbin.GetResponse;
import yd.service.HttpDemo;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

  @Spy @InjectMocks HttpDemo httpDemo = new HttpDemo();

  @Mock
  RestTemplate restTemplate;

  @Test
  public void foo() {
    GetResponse value = new GetResponse();
    ResponseEntity<GetResponse> resp = new ResponseEntity<GetResponse>(value, HttpStatus.OK);
    when(restTemplate.exchange(
        anyString(),
        eq(GET),
        anyObject(),
        Mockito.<Class<GetResponse>>any()
    )).thenReturn(resp);

    try {
      Assert.assertSame(httpDemo.fetchJSON().get(), value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
