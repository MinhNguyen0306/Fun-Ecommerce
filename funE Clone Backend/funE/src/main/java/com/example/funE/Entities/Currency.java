package com.example.funE.Entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Table(name = "currency")
@Getter @Setter
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private int id;
    private String currencyName;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
    private List<Product> products;

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}
