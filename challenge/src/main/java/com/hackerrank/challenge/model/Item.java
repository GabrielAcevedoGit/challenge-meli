package com.hackerrank.challenge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, length = 2500)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Embedded
    private Price price;

    @Column(name = "stock")
    private Integer stock = 0;

    @Column(name = "sold_quantity")
    private Integer soldQuantity = 0;

    @ElementCollection
    @CollectionTable(name = "item_images", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "image_url")
    private List<String> images;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "item_id")
    private List<ItemAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
