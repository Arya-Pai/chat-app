package com.project.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.model.AuthRes;
import com.project.model.LoginDTO;
import com.project.model.RegisterDTO;
import com.project.model.User;
import com.project.repo.UserRepo;
import com.project.util.auth;
@Service
public class AuthService {
	private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final auth jwtService;
    
    public AuthService(UserRepo userRepo,PasswordEncoder passwordEncoder, auth jwtService) {
    	this.userRepo=userRepo;
    	this.passwordEncoder=passwordEncoder;
    	this.jwtService=jwtService;
    }
    
    public void register(RegisterDTO req) {
    	if(userRepo.findByEmail(req.getEmail()).isPresent()) {
    		throw new RuntimeException("Email already exists");
    	}
    	User user=new User();
    	user.setUsername(req.getUsername());
    	user.setEmail(req.getEmail());
    	user.setPassword(passwordEncoder.encode(req.getPassword()));
    	userRepo.save(user);
    	
    }
    public AuthRes login(LoginDTO req) {
    	 Optional<User> optionalUser = userRepo.findByEmail(req.getEmail());

    	    if (!optionalUser.isPresent()) {
    	        throw new RuntimeException("Email or Password incorrect");
    	    }
    	   User user=optionalUser.get();
    	if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email or Password incorrect");
        }
    	String token = jwtService.generateToken(user.getEmail());
    	return new AuthRes(token,user.getUsername(),user.getRole(),jwtService.getExpirationTime());
    }
	
}
