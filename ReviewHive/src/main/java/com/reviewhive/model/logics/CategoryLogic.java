package com.reviewhive.model.logics;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.reviewhive.model.dao.entity.CategoryDetailsEntity;
import com.reviewhive.model.dao.entity.CategoryEntity;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@Service
public interface CategoryLogic {

	/**
	 * Save Category
	 * @param inDto
	 */
	public void saveCategory(CategoryEntity entity);
	
	/**
	 * Get All Category
	 * @param page
	 */
	public List<CategoryDetailsEntity> getAllCategory(int page);
	
	/**
	 * Update Category Status
	 * @param status
	 */
	public void updateCategoryStatus(boolean status, int id);
	
	/**
	 * Update Category Status
	 * @param status
	 */
	public void updateCategoryAll(String categoryName,
			String categoryDescription,
			String categoryColor,
			boolean categoryStatus,
			Timestamp updatedDate, 
			int id);
	
	/**
	 * Get Category by its id
	 * @param id
	 * @return
	 */
	public CategoryEntity getCategoryById(int id);
}