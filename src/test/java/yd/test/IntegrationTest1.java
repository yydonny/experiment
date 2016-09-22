package yd.test;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(Bootstrap.class)
//@WebIntegrationTest(randomPort = true)
public class IntegrationTest1 {
  final RestTemplate restTemplate = new TestRestTemplate();

  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

//  @Test
  public void getAccount() throws Exception {
    this.mockMvc.perform(post("/akka")
        .accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=UTF-8"))
        .andExpect(content().string("it works!"));
  }
}
