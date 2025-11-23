package com.hackerrank.challenge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_attribute")
@Getter @Setter
@NoArgsConstructor
public class ItemAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attribute_name")
    private String name;

    @Column(name = "attribute_value")
    private String value;

    public ItemAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
