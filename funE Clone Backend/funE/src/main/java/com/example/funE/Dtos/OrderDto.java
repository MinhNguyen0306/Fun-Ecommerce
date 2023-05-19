package com.example.funE.Dtos;

import com.example.funE.Entities.Address;
import com.example.funE.Entities.OrderItem;
import com.example.funE.Entities.Payment;
import com.example.funE.Entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private int id;
    private LocalDate dateCreated;
    private PaymentDto payment;

    private User user;
    private List<Address> addressList;

    @JsonManagedReference
    private List<OrderItem> orderItems = new ArrayList<>();
}
