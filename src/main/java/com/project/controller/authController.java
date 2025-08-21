package com.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.AuthRes;
import com.project.model.LoginDTO;
import com.project.model.RegisterDTO;
import com.project.service.AuthService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class authController {
	private final AuthService authService;

    public  authController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/registerController")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterDTO req,BindingResult result) {
    	 if (result.hasErrors()) {
    	        List<String> errors = result.getAllErrors()
    	            .stream()
    	            .map(DefaultMessageSourceResolvable::getDefaultMessage)
    	            .toList();
    	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    	    }
        authService.register(req); // just saves user, no token returned
        return ResponseEntity.ok(Map.of("message", "Registration successful","errors", List.of()));
    }
    @PostMapping("/loginController")
    public ResponseEntity<AuthRes> login(@RequestBody LoginDTO req) {
        return ResponseEntity.ok(authService.login(req));
        
    }
    
    
    
	
}
