package br.com.rest.model.response;

import io.restassured.response.Response;
import lombok.Data;

@Data
public class LoginResponse extends BaseResponse {

    private String authorization;
    private String email;
    private String password;
}
