package com.example.funE.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@RequiredArgsConstructor
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    private String productName;
    private String description;
    private double pricing;
    private int stock;
    private boolean isPublicSearchable;
    private boolean isShowInLive;

    @JsonIgnore
    @ManyToOne
    private Category category;

    @JsonIgnore
    @ManyToOne
    private Currency currency;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<CartItem> cartItemList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Photo> photos;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Video> videos;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Audio> audioSet;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", pricing=" + pricing +
                ", stock=" + stock +
                ", isPublicSearchable=" + isPublicSearchable +
                ", isShowInLive=" + isShowInLive +
                ", category=" + category +
                ", currency=" + currency +
                ", photos=" + photos +
                ", videos=" + videos +
                ", audioSet=" + audioSet +
                '}';
    }
}
