package com.peterjurkovic.common.domain;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceRule {
    
    private final static String SEPARATOR = "#";
    
    private String product;
    private String provider;
    private String country;
    private String account;
    private String price;
    
    public static String productId(String product) {
        return new PriceRule(product, null, null, null, null).getId();
    }
    
    public static String providerId(String product, String provider) {
        return new PriceRule(product, provider, null, null, null).getId();
    }
    
    public String getId() {
        return new StringBuilder()
                    .append(trimToEmpty(product))
                    .append(SEPARATOR)
                    .append(trimToEmpty(provider))
                    .append(SEPARATOR)
                    .append(trimToEmpty(country))
                    .append(SEPARATOR)
                    .append(trimToEmpty(account))
                    .toString();
    }
    
}
