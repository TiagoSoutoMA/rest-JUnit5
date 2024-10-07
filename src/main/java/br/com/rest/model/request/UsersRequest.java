package br.com.rest.model.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequest {

    private String nome;
    private String email;
    private String password;
    private String administrador;
}
