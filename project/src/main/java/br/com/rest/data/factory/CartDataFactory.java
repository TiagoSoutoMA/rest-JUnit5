package br.com.rest.data.factory;

import br.com.rest.model.request.CartRequest;
import br.com.rest.model.request.ProductCartRequest;
import br.com.rest.model.request.ProductsRequest;
import br.com.rest.utils.Manipulation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class CartDataFactory {

    private static Properties prop = Manipulation.getProp();

    public static String idCarrinho() { return prop.getProperty("idCarrinho"); }

    public static String name() { return DataFaker.name(); }
    public static Integer quantity() { return DataFaker.quantity(); }
    public static String empty() { return DataFaker.empty(); }

    public static CartRequest createCart(String idProduct, Integer quantity) {

        CartRequest cartRequest = new CartRequest();

        ProductCartRequest product = new ProductCartRequest(idProduct, DataFaker.quantityValid(quantity));

        cartRequest.setProdutos(Collections.singletonList(product));

        return cartRequest;
    }

    public static CartRequest createCartIncorrectId(String idProduct, Integer quantity) {

        CartRequest cartRequest = new CartRequest();

        ProductCartRequest product = new ProductCartRequest(idProduct, DataFaker.quantityValid(quantity));

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
