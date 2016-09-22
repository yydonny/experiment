package yd.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login {
  @RequestMapping("/login")
  public String loginPage(Model model) {
    model.addAttribute("loginPath", "/login");
    return "login";
  }
}
