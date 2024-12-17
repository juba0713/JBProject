package com.jbproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping
	public String showHome() {
		return "home";
	}
	
	@GetMapping("/about")
	public String showAbout() {
		
		return "";
	}
	
	@GetMapping("/resume")
	public String showResume() {
		
		return "";
	}
	
	@GetMapping("/cv")
	public String showCV() {
		
		return "";
	}
	
	
	@GetMapping("/pro-request")
	public String showProRequest() {
		return "pro-request/login";
	}
}
