package com.jbproject.controllers.prorequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pro-request")
public class HomeController {
	
	@GetMapping("/home")
	public String showUserHome() {
		
		return "pro-request/user/home";
	}
}
