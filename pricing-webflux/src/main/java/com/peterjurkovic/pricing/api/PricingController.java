package com.peterjurkovic.pricing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.peterjurkovic.common.PriceResponse;
import com.peterjurkovic.common.domain.Account;
import com.peterjurkovic.common.domain.Cost;
import com.peterjurkovic.common.domain.Price;
import com.peterjurkovic.pricing.accounts.AccountsHttpClient;
import com.peterjurkovic.pricing.cost.CostHttpClient;
import com.peterjurkovic.pricing.repository.PricingRepository;

import reactor.core.publisher.Mono;

@RestController
public class PricingController {
    
    @Autowired
    private AccountsHttpClient accountsClient;
  
    
    @Autowired CostHttpClient costClient;
    @Autowired
    private PricingRepository pricingRepository;
    
    @GetMapping("/account/{id}")
    public Mono<Account> getAccount(@PathVariable String id){
        return accountsClient.getById(id);
    }
    
    @GetMapping("/price/{product}/{account}")
    public Mono<PriceResponse> getProductPrice(@PathVariable String product,@PathVariable String account){
        return Mono.zip(
                        pricingRepository.findForProduct(product),
                        
                        costClient.getCost(),
                        
                        accountsClient.getById(account))
                .map(this::toResponse);
    }
    
    @GetMapping("/cost")
    public Mono<Cost> getCost(){
        return costClient.getCost();
                    
    }
    
    
    private PriceResponse toResponse(reactor.util.function.Tuple3<Price, Cost, Account> tuple) {
        return new PriceResponse(tuple.getT1(), tuple.getT2(), tuple.getT3()); 
        
    }
   
}
