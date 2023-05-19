package com.example.funE.Services.Impl;

import com.example.funE.Dtos.CurrencyDto;
import com.example.funE.Entities.Currency;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.CurrencyRepo;
import com.example.funE.Services.CurrencyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyRepo currencyRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CurrencyDto getCurrencyById(Integer currencyId) {
        Currency currency = this.currencyRepo.findById(currencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Currency", "Id", currencyId));
        return this.modelMapper.map(currency, CurrencyDto.class);
    }

    @Override
    public List<CurrencyDto> getAllCurrency() {
        List<Currency> currencyList = this.currencyRepo.findAll();
        List<CurrencyDto> currencyDtoList = currencyList.stream()
                .map(currency -> currencyToDto(currency)).collect(Collectors.toList());
        return currencyDtoList;
    }

    private Currency dtoToCurrency(CurrencyDto currencyDto) {
        return this.modelMapper.map(currencyDto, Currency.class);
    }
    private CurrencyDto currencyToDto(Currency currency) {
        return this.modelMapper.map(currency, CurrencyDto.class);
    }
}
