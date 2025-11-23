package com.hackerrank.challenge.model;

import com.hackerrank.challenge.exception.InvalidDataException;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Embeddable
@Getter
public class Price {

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    protected Price() {}

    public Price(Currency currency, BigDecimal amount) {
        if (currency == null) {
            throw new InvalidDataException("currency cannot be null");
        }
        if (amount == null) {
            throw new InvalidDataException("amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException("amount must be greater than zero");
        }
        this.currency = currency;
        this.amount = amount;
    }


    public String formattedAmount(){
        return currency.getValueCurrency() + " " + amount;
    }
}
