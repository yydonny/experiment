package yd.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import yd.model.MyResult;
import yd.service.HttpDemo;

@RequestMapping("/http")
@RestController
public class HttpDemoController {

  private final HttpDemo httpDemo;

  @Autowired
  public HttpDemoController(HttpDemo httpDemo) {
    this.httpDemo = httpDemo;
  }

  @RequestMapping
  @ResponseBody
  public DeferredResult<MyResult> foo() {
    final DeferredResult<MyResult> retval = new DeferredResult<>();
    httpDemo.fetchAll().subscribe(
        myResult -> {
          retval.setResult(myResult);
        },
        ex -> { // if any of the backend rest calls fail
          retval.setErrorResult(ex);
        }
    );
    return retval;
  }
}
