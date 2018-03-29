package com.peterjurkovic.common.domain; 

import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class PriceRules {
    
    private final static String DEFAULT_DEFAULT_PRICE_ID = new PriceRule().getId();

    private final Map<String, String> pricing;
      
      
    public PriceRules(List<PriceRule> rules) {
        this.pricing = rules.stream()
                            .collect(toMap(PriceRule::getId, PriceRule::getPrice));
    }
    
    public BigDecimal getProductPrice(String product) {
        String price = this.pricing.get(PriceRule.productId(product));
        if(price == null) {
            price = getDefaultPriceOrThrow();
        }
        return new BigDecimal(price);
    }
    
    public BigDecimal getProviderPrice(String product, String provider) {
        String price = this.pricing.get(PriceRule.providerId(product, provider));
        if(price == null) {
            return getProductPrice(product);
        }
        return new BigDecimal(price);
    }
    
    
    private String getDefaultPriceOrThrow() {
        String price = this.pricing.get(DEFAULT_DEFAULT_PRICE_ID);
        if(price == null)
                throw new IllegalStateException("No default price found");
        return price;
    }
}
