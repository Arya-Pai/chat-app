package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ViewController {
	@GetMapping("/login")
    public String loginPage() {
        return "redirect:/views/login.html"; 
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/views/index.html"; 
    }
    
    @GetMapping("/")
    public String indexPage() {
        return "redirect:/views/index.html";
    }
}
