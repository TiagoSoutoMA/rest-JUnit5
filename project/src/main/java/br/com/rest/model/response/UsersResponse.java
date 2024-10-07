package br.com.rest.model.response;

import br.com.rest.model.request.UsersRequest;
import lombok.Data;

import java.util.List;

@Data
public class UsersResponse extends BaseResponse {

    private String _id;
    private String nome;
    private String email;
    private String password;
    private String administrador;
    private int quantidade;
    private List<UsersResponse> usuarios;
    private String idCarrinho;
}
