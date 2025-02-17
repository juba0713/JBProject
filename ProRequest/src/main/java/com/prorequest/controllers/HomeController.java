package com.prorequest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//Test
@Controller
@RequestMapping("/pro-request")
public class HomeController {
	
	@GetMapping("/home")
	public String showUserHome() {
		
		return "pro-request/user/home";
	}
	
	@GetMapping("/admin/home")
	public String showAdminHome() {
		
		return "pro-request/admin/home";
	}
}
