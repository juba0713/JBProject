package com.jbproject.controllers.prorequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pro-request/admin")
public class UserController {
	
	@GetMapping("/employee")
	public String showEmployeeUser() {
		
		return "pro-request/admin/employee";
	}
}
