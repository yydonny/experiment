package yd.web.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Customized error handler and view
 * This is a Spring Boot feature, not Spring MVC
 */
@Component
@Controller
@RequestMapping("error")
public class MyErrorController implements ErrorController {
  @Autowired ErrorAttributes errorAttributes;

  @RequestMapping
  public String errorHtml(
      HttpServletRequest request,
      HttpServletResponse response,
      Model model
  ) {
    Map<String, Object> errorModel =
        errorAttributes.getErrorAttributes(new ServletRequestAttributes(request), false);
    response.setStatus(getStatus(request).value());
    model.addAllAttributes(errorModel);
    return "error1"; // if the view name is `error`, it will be eclipsed by the default error view of spring boot(?)
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    try {
      return HttpStatus.valueOf(statusCode);
    } catch (Exception ex) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }

  @Override
  public String getErrorPath() {
    return "error";
  }
}
