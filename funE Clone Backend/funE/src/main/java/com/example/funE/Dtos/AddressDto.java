package com.example.funE.Dtos;

import com.example.funE.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;

@AllArgsConstructor @Getter @Setter
public class AddressDto {
    private int id;
    private int zipCode;
    private String floor;
    private int blockNumber;
    private String roadName;
    private String building;
    private String state;
    private String unit;
    private String city;
}
