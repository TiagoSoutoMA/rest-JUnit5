package br.com.rest.tests.cart;

import br.com.rest.client.CartClient;
import br.com.rest.data.factory.BaseDataFactory;
import br.com.rest.data.factory.CartDataFactory;
import br.com.rest.model.request.CartRequest;
import br.com.rest.model.response.CartResponse;
import br.com.rest.utils.messages.CartMessage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

@DisplayName("GetCart")
public class GetCartTest {

    private CartClient cartClient = new CartClient();
    private CartRequest cartRequest;
    private CartResponse cartResponse;

    @Test
    @DisplayName("CE001 - Validate cart list sucessfully")
    @Tag("functional")
    public void testListCartSucessfully() {

        Response response = cartClient.cartGet()
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, cartResponse.getStatusCode(), "Status code deve ser 200"),
                () -> Assertions.assertFalse(cartResponse.getCarrinhos().isEmpty(), "A lista de usuários não deve ser vazia"),
                () -> Assertions.assertEquals(cartResponse.getQuantidade(), cartResponse.getCarrinhos().size(), "A quantidade deve ser igual ao tamanho da lista")
        );
    }

    @Test
    @DisplayName("CE002 - Validate cart list by id sucessfully")
    @Tag("functional")
    public void testListCartByIdSucessfully() {

        Response response = cartClient.cartIdGet(BaseDataFactory.idCarrinhoProp())
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, cartResponse.getStatusCode(), "Status code deve ser 200"),
                () -> Assertions.assertFalse(cartResponse.getProdutos().isEmpty(), "A lista de usuários não deve ser vazia")
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate cart list with incorrect id")
    @Tag("functional")
    public void testListCartIncorrectId() {

        Response response = cartClient.cartIdGet(BaseDataFactory.name())
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, cartResponse.getStatusCode(), "Status code deve ser 400"),
                () -> Assertions.assertEquals(CartMessage.CART_NOT_FOUND, cartResponse.getMessage())
        );
    }
}
