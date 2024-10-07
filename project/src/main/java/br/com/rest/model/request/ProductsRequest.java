package br.com.rest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsRequest {

    private String nome;
    private Integer preco;
    private String descricao;
    private Integer quantidade;
}
