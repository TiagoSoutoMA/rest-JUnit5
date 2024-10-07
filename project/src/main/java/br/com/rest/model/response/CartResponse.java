package br.com.rest.model.response;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse extends BaseResponse {

    private int quantidade;
    private List<Cart> carrinhos;
    private List<Product> produtos;
    private Integer precoTotal;
    private Integer quantidadeTotal;
    private String idUsuario;
    private String _id;
    private Item item;

    @Data
    public static class Cart {

        private List<Product> produtos;
        private int precoTotal;
        private int quantidadeTotal;
        private String idUsuario;
        private String _id;
    }

    @Data
    public static class Product {

        private String idProduto;
        private int quantidade;
        private double precoUnitario;
    }

    @Data
    public static class Item {

        private String idProduto;
        private int quantidade;
        private int index;
        private int quantidadeEstoque;
    }
}
