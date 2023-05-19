package com.example.funE.Services.Impl;

import com.example.funE.Dtos.PaymentDto;
import com.example.funE.Entities.Payment;
import com.example.funE.Entities.User;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.PaymentRepo;
import com.example.funE.Repositories.UserRepo;
import com.example.funE.Services.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaymentDto addPayment(PaymentDto paymentDto) {
        Payment payment = this.modelMapper.map(paymentDto, Payment.class);
        List<Payment> payments = paymentRepo.findAll();
        if(payments == null || payments.isEmpty()) {
            payment.setDefault(true);
        }
        Payment addedPayment = paymentRepo.save(payment);
        return modelMapper.map(addedPayment, PaymentDto.class);
    }

    @Override
    public String setPaymentDefault(Integer paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "Id", paymentId));
        if(payment.isDefault()) {
            return "failed";
        }
        payment.setDefault(true);
        return "success";
    }

    @Override
    public PaymentDto getPaymentDefault(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Optional<Payment> paymentDefaultOptional = user.getPayments().stream()
                .filter(payment -> payment.isDefault()).findAny();
        if(paymentDefaultOptional.isPresent()) {
            return modelMapper.map(paymentDefaultOptional.get(), PaymentDto.class);
        }
        return null;
    }

    @Override
    public void deletePayment(Integer paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "Id", paymentId));
        List<Payment> payments = paymentRepo.findAll();
        paymentRepo.delete(payment);
    }

    @Override
    public List<PaymentDto> getAllPayment(Integer userId) {
        List<Payment> payments = paymentRepo.findAll();
        List<Payment> paymentsOfUser = payments.stream()
                .filter(payment -> payment.getUser().getId() == userId).collect(Collectors.toList());
        List<PaymentDto> paymentDtoList = paymentsOfUser.stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class)).collect(Collectors.toList());
        return paymentDtoList;
    }
}
