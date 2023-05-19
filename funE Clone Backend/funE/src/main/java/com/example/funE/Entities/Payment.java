package com.example.funE.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int id;
    private String nameOnCard;
    private int creditCardNumber;
    @JsonFormat(pattern = "MM/yyyy")
    private Date expiresOn;
    private int cvv;
    private int zipCode;
    private String billingAddress;
    private String state;
    private String city;

    @Column(columnDefinition = "bit default 0")
    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY)
    private List<Order> orders;
}
