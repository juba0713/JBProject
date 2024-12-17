package com.prorequest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class UserController {
	
	@GetMapping("/employee")
	public String showEmployeeUser() {
		
		return "admin/employee";
	}
}
