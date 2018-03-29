package com.peterjurkovic.common.domain;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Price{
    
    private final BigDecimal value;
    
    public static Price of(BigDecimal value) {
        return new Price(requireNonNull(value));
    }
    
    public String asString() {
        return value.toPlainString();
    }
}