package com.example.funE.Services;

import com.example.funE.Dtos.PaymentDto;
import com.example.funE.Entities.Payment;

import java.util.List;

public interface PaymentService {
    PaymentDto addPayment(PaymentDto paymentDto);
    String setPaymentDefault(Integer paymentId);
    PaymentDto getPaymentDefault(Integer userId);
    void deletePayment(Integer paymentId);
    List<PaymentDto> getAllPayment(Integer userId);
}
