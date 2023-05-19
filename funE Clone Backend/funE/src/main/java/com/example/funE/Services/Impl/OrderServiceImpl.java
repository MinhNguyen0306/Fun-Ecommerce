package com.example.funE.Services.Impl;

import com.example.funE.Dtos.OrderDto;
import com.example.funE.Entities.Order;
import com.example.funE.Entities.User;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.OrderRepo;
import com.example.funE.Repositories.UserRepo;
import com.example.funE.Services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderItems(orderDto.getOrderItems());
        order.setDateCreated(LocalDate.now());
        order.setUser(orderDto.getUser());
        order.setPayment(order.getPayment());
        order.setAddressList(orderDto.getAddressList());
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByUserId(Integer userId) {
        return null;
    }
}
