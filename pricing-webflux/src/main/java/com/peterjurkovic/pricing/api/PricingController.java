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
    
    @Autowired // <- injected by a framework
    private AccountsHttpClient accountsClient;
    
    @Autowired
    private CostHttpClient costClient;
    
    @Autowired
    private PricingRepository pricingRepository;
    
    @GetMapping("/account/{id}")
    public Mono<Account> getAccount(@PathVariable String id){
        return accountsClient.getById(id);
    }
    
    @GetMapping("/price/{product}/{account}")
    public Mono<PriceResponse> getProductPrice(@PathVariable String product,@PathVariable String account){
        // this will be loaded in parallel
        return Mono.zip(
                        // calculated from an app memory
                        pricingRepository.findForProduct(product),
                        // it issue HTTP call to phub
                        costClient.getCost(),
                        // HTTP call to account service
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
