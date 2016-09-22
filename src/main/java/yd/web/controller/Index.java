package yd.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Index {
  @RequestMapping
  public String index(Model model) {
    model.addAttribute("title", "Fancy Template");
    model.addAttribute("counter", 100);
    return "index";
  }
}
