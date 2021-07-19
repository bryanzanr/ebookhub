package com.herokuapp.ebookhub.category.usecases;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

import com.herokuapp.ebookhub.category.entities.Category;
import com.herokuapp.ebookhub.category.entities.CategoryRepository;

import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;

public class GetCategoryUseCase {

    private CategoryRepository categoryRepository;

    public GetCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<List<Object>> GetCategories(String category, String subCategory, Long no) {
		List<Object> response = new ArrayList<Object>();
        List<Category> categories = new ArrayList<Category>();
        if (category != null && category != "") {
            categories = categoryRepository.findByCategory(category);
        }else {
            if (subCategory != null && subCategory != "") {
                categories = categoryRepository.findBySubCategory(subCategory);   
            }else {
                if (no != null) {
                    categories.add(categoryRepository.findById(no).get());
                }else {
                    categories = categoryRepository.findAll();
                }
            }
        }
		try {
			for (int i = 0; i < categories.size(); i++) {
                Map<String, Object> entity = new HashMap<String, Object>();
                entity.put("data", categories.get(i));	
                response.add(entity);
			}
            return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}