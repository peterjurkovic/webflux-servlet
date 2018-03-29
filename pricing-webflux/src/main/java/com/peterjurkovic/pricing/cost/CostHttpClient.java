package com.peterjurkovic.pricing.cost;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.peterjurkovic.common.BaseResponse;
import com.peterjurkovic.common.domain.Cost;
import com.peterjurkovic.pricing.accounts.AccountsHttpClient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import reactor.core.publisher.Mono;

@Component
public class CostHttpClient {

    private static final Logger log = LoggerFactory.getLogger(AccountsHttpClient.class);
    
    private final WebClient client;
    
    public CostHttpClient() {
        client = WebClient.builder()
                .baseUrl("http://phub.qa:8019/provisioning")
                .build();
    }
       
            
    public Mono<Cost> getCost(){
        return client
                .get()
                .uri("/json?cmd=get-mt-cost-for-message&product=0&gateway=tele42&msisdn-prefix=421&network=23101")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CostResponse.class)
                .doOnNext(cost -> log.debug("Cost loaded" + cost))
                .map( CostResponse::getCost);
    }
    
    
    @Data
    @EqualsAndHashCode(callSuper = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class CostResponse extends BaseResponse{
        @JsonProperty("price-rule")
        private Cost cost;
    }
}
