package com.example.funE.Dtos;

import com.example.funE.Entities.Product;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyDto {
    private int id;
    private String currencyName;

    @Override
    public String toString() {
        return "CurrencyDto{" +
                "id=" + id +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}
