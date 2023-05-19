package com.example.funE.Controllers;

import com.example.funE.Dtos.CurrencyDto;
import com.example.funE.Services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/currency")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("")
    public ResponseEntity<List<CurrencyDto>> getAllCurrency() {
        List<CurrencyDto> currencyDtoList = this.currencyService.getAllCurrency();
        return new ResponseEntity<>(currencyDtoList, HttpStatus.OK);
    }

    @GetMapping("/{currencyId}")
    public ResponseEntity<CurrencyDto> getCurrencyById(@PathVariable Integer currencyId) {
        CurrencyDto currencyDto = this.currencyService.getCurrencyById(currencyId);
        return new ResponseEntity<>(currencyDto, HttpStatus.OK);
    }
}
