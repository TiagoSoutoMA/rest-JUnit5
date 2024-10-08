package br.com.rest.data.factory;

import br.com.rest.model.request.CartRequest;
import br.com.rest.model.request.ProductCartRequest;

import java.util.Collections;

public class CartDataFactory {

    public static CartRequest createCart(String idProduct, Integer quantity) {

        CartRequest cartRequest = new CartRequest();

        ProductCartRequest product = new ProductCartRequest(idProduct, BaseDataFactory.validQuantity(quantity));

        cartRequest.setProdutos(Collections.singletonList(product));

        return cartRequest;
    }

    public static CartRequest createCartIncorrectId(String idProduct, Integer quantity) {

        CartRequest cartRequest = new CartRequest();

        ProductCartRequest product = new ProductCartRequest(idProduct, BaseDataFactory.validQuantity(quantity));

        cartRequest.setProdutos(Collections.singletonList(product));

        return cartRequest;
    }

    public static CartRequest createCartIncorrectQuantity(String idProduct, Integer quantity) {

        CartRequest cartRequest = new CartRequest();

        ProductCartRequest product = new ProductCartRequest(idProduct, quantity + 1);

        cartRequest.setProdutos(Collections.singletonList(product));

        return cartRequest;
    }
}
