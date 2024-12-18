package com.reviewhive.model.dto;

import java.util.List;

import com.reviewhive.model.dao.entity.CategoryDetailsEntity;
import com.reviewhive.model.dao.entity.CategoryEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@Data
public class CategoryDto {
	
	private int id;
	
	@NotEmpty(message="AW")
	private String categoryName;
	
	@NotBlank(message="Category Color is required!")
	private String categoryColor;
	
	@NotBlank(message="Category Status is required!")
	private String categoryStatus;
	
	@NotBlank(message="Category Description is required!")
	private String categoryDescription;
	
	private CategoryEntity category;
	
	private List<CategoryDetailsEntity> categories;
	
	private int page;
	
	private String updateType;
}
