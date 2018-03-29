package com.peterjurkovic.pricing.accounts;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("nexmo.accounts")
public class AccountsConfig {

    private String host;
    private int port;
    private String key;

    public String getBaseUrl() {
        return "http://" + getHost() + ":" + getPort();
    }
}
