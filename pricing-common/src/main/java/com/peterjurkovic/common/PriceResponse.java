package com.peterjurkovic.common;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.peterjurkovic.common.domain.Account;
import com.peterjurkovic.common.domain.Cost;
import com.peterjurkovic.common.domain.Price;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PriceResponse {
    
    private String price;
    private String cost = "0.0000";
    private String currency = "EUR";
    private String account;
    
    public PriceResponse(Price price) {
        this.price = requireNonNull(price).asString();
        
    }
    
    public PriceResponse(Cost cost) {
        this.cost = requireNonNull(cost).asString();
        
    }
    
    public PriceResponse(Price price, Cost cost, Account account) {
        this(price);
        this.cost = requireNonNull(cost).asString();
        this.account = account + " from accounts.qa";
        
    }
    
  
}
