package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/register")
    public String registerPage() {
    	return "redirect:/views/registration.html";
    }
    
    @GetMapping("/chatRoom/{id}")
    public String chatRoom(@PathVariable Long id,Model model ) {
    	model.addAttribute("chatRoomId",id);
    	return"redirect:/views/chatRoom.html";
    }
}
