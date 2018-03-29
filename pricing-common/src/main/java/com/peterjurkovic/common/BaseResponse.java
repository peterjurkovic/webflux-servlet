package com.peterjurkovic.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BaseResponse {

    @JsonProperty("result-code")
    int resultCode;
}
