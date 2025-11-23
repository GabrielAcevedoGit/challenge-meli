package com.hackerrank.challenge.model;
import lombok.Getter;

public enum Currency {
    ARS("$"),
    USD("US$"),
    UYU("$"),
    BRL("R$"),
    MXN("$"),
    CLP("$"),
    COP("$");

    @Getter
    private final String valueCurrency;

    Currency(String valueCurrency) {
        this.valueCurrency = valueCurrency;
    }
}

