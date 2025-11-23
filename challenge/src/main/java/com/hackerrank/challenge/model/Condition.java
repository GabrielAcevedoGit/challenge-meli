package com.hackerrank.challenge.model;

import lombok.Getter;

public enum Condition {
    NEW("Nuevo"),
    USED("Usado"),
    REFURBISHED("Reacondicionado"),
    OPEN_BOX("Caja Abierta");

    @Getter
    private String description;

    Condition(String description) {
        this.description = description;
    }

}
