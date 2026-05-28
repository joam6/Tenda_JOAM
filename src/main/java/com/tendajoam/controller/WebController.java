package com.tendajoam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String redirectToMain() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "forward:/index.html";
    }
    
    @GetMapping("/login")
    public String loginPage() {
    	return "forward:/login.html";
    }
    
    @GetMapping("/personal")
    public String personalPage() {
    	return "forward:/personal.html";
    }  
    
    @GetMapping("/register")
    public String registerPage() {
    	return "forward:/register.html";
    }
    
    @GetMapping("/register-seller")
    public String registerSellerPage() {
    	return "forward:/registre-venedor.html";
    }
}
