package com.example.funE.Dtos;

import com.example.funE.Entities.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
public class ProductDto{
    private int id;
    private String productName;
    private String description;
    private double pricing;
    private int stock;
    private CategoryDto category;
    private CurrencyDto currency;

    private Set<PhotoDto> photos;
    private Set<VideoDto> videos;
    private Set<AudioDto> audioSet;


    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", pricing=" + pricing +
                ", stock=" + stock +
//                ", category=" + category +
//                ", currency=" + currency +
                '}';
    }
}
