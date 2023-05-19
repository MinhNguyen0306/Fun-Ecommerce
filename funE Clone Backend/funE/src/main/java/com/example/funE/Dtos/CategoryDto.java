package com.example.funE.Dtos;

import com.example.funE.Entities.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter @Setter
public class CategoryDto {
    private int id;
    private String categoryName;
}
