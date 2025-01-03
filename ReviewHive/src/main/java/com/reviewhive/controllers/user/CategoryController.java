package com.reviewhive.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

	@GetMapping("/")
	public String showHomePage() {
		
		return "home";
	}
}
