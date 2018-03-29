package com.peterjurkovic.pricing.repository;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.peterjurkovic.common.domain.Price;
import com.peterjurkovic.common.domain.PriceRule;
import com.peterjurkovic.common.domain.PriceRules;
import com.peterjurkovic.common.utils.JsonUtils;

import reactor.core.publisher.Mono;

@Component
public class PricingRepository {

    private final PriceRules priceRules;
    
    private static final Logger log = LoggerFactory.getLogger(PricingRepository.class);
    

    public PricingRepository(@Value("classpath:pricing.json")  Resource priceList ) {
        Assert.isTrue(priceList.exists(), "It looks like pricing.json is missing");
        List<PriceRule> list;
        try {
            list = JsonUtils.toList(priceList.getInputStream(), PriceRule.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        this.priceRules = new PriceRules(list);
    }
    
    public Mono<Price> findForProduct(String product){
       return Mono.just(Price.of(priceRules.getProductPrice(product)))
                   .doOnNext( price -> log.debug("Resolved price {} for product {}",price,product) );
    }

}
