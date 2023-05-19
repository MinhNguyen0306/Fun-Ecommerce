package com.example.funE.Services;

import com.example.funE.Dtos.OrderDto;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByUserId(Integer userId);
}
