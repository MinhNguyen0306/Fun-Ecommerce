package com.example.funE.Services;

import com.example.funE.Dtos.CurrencyDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CurrencyService {
    CurrencyDto getCurrencyById(Integer currencyId);
    List<CurrencyDto> getAllCurrency();
}
