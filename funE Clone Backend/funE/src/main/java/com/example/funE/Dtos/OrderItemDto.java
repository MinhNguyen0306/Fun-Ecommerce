package com.example.funE.Dtos;

import com.example.funE.Entities.Order;
import com.example.funE.Entities.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class OrderItemDto {
    private int id;
    private int quantity;
    private int status;

    private ProductDto product;

    @JsonBackReference
    private OrderDto order;
}
