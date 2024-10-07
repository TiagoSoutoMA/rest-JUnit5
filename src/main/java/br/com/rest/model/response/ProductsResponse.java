package br.com.rest.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductsResponse extends BaseResponse {

    private String nome;
    private String preco;
    private String descricao;
    private String quantidade;
    private String _id;
    private List<ProductsResponse> produtos;
    private List<String> idCarrinhos;
}
