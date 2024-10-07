package br.com.rest.model.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartRequest {

    private String idProduto;
    private int quantidade;
}
