package com.example.funE.Dtos;

import com.example.funE.Entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemDto {
    private int id;
    private int quantity;
    private ProductDto product;
}
