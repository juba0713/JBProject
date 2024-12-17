package com.reviewhive.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

	@GetMapping("/category-list")
	public String showCategoriesScreen() {
		return "admin/category-list";
	}
}
