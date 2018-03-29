package com.peterjurkovic.pricing.accounts;

import static java.util.Collections.singletonMap;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.peterjurkovic.common.BaseResponse;
import com.peterjurkovic.common.domain.Account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import reactor.core.publisher.Mono;

@Component
public class AccountsHttpClient {
 
    private static final Logger log = LoggerFactory.getLogger(AccountsHttpClient.class);
    
    private final WebClient client;
    public AccountsHttpClient(AccountsConfig config) {
        client = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .defaultUriVariables(singletonMap("key", config.getKey()))
                .build();
    }
    
    public Mono<Account> getById(String id){

        return client
                .get()
                .uri("/accounts/json?cmd=get&key={key}&sysid={sysid}", singletonMap("sysid", id))
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AccountResponse.class)
                .doOnNext(acc -> log.debug("Account loaded" + acc))
                .map( AccountResponse::getAccount);
                
            
        
    }
    
    
    
    @Data
    @EqualsAndHashCode(callSuper = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class AccountResponse extends BaseResponse{
        private Account account;
        
        
    }
}
