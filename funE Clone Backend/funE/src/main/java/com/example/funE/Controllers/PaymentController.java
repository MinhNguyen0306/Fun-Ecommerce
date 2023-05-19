package com.example.funE.Controllers;

import com.example.funE.Dtos.PaymentDto;
import com.example.funE.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<PaymentDto> addPayment(@RequestBody PaymentDto paymentDto) {
        PaymentDto savedPayment = paymentService.addPayment(paymentDto);
        if(savedPayment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    @PostMapping("/default")
    public ResponseEntity<String> setPaymentDefault(@RequestParam("paymentId") Integer paymentId) {
        String result = paymentService.setPaymentDefault(paymentId);
        if(result.equals("failed")) {
            return ResponseEntity.status(500).body("Internal server error");
        }
        return ResponseEntity.ok("Set this payment as default success");
    }

    @DeleteMapping("/payment")
    public ResponseEntity<Void> deletePayment(@RequestParam("paymentId") Integer paymentId) {
        this.paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<PaymentDto>> getAllPayment(@RequestParam("userId") Integer userId) {
        List<PaymentDto> paymentDtoList = paymentService.getAllPayment(userId);
        return ResponseEntity.ok(paymentDtoList);
    }

    @GetMapping("/default")
    public ResponseEntity<PaymentDto> getPaymentDefault(@RequestParam("userId") Integer userId) {
        PaymentDto paymentDefault = paymentService.getPaymentDefault(userId);
        if(paymentDefault == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paymentDefault, HttpStatus.OK);
    }
}
