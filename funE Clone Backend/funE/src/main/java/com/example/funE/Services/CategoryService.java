package com.example.funE.Services;

import com.example.funE.Dtos.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDto getCategoryById(Integer categoryId);
    List<CategoryDto> getAllCategory();
}
