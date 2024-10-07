package br.com.rest.model.response;

import lombok.Data;

@Data
public class BaseResponse {

    private int statusCode;
    private String message;
}
