package com.peterjurkovic.common.domain;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Cost {

    @JsonProperty("price")
    BigDecimal value;
    
    public static Cost of(BigDecimal value) {
        return new Cost(requireNonNull(value));
    }
    public String asString() {
        return value.toPlainString();
    }
    
}
