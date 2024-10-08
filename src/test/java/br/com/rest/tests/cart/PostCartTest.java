package br.com.rest.tests.cart;

import br.com.rest.client.CartClient;
import br.com.rest.client.LoginClient;
import br.com.rest.client.ProdutctsClient;
import br.com.rest.client.UsersClient;
import br.com.rest.data.factory.*;
import br.com.rest.model.request.CartRequest;
import br.com.rest.model.request.LoginRequest;
import br.com.rest.model.request.ProductsRequest;
import br.com.rest.model.request.UsersRequest;
import br.com.rest.model.response.CartResponse;
import br.com.rest.utils.messages.CartMessage;
import br.com.rest.utils.messages.ProductsMessage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

@DisplayName("PostCart")
public class PostCartTest {

    private static CartClient cartClient = new CartClient();
    private static ProdutctsClient produtctsClient = new ProdutctsClient();
    private static LoginClient loginClient = new LoginClient();
    private static UsersClient usersClient = new UsersClient();
    private CartRequest cartRequest;
    private CartResponse cartResponse;
    private static String idProduct, idUser, token;
    private static Integer quantityProduct;

    @BeforeAll
    public static void setUp() {

        UsersRequest usersRequest = UsersDataFactory.createUserAdmin();

        idUser = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .path("_id")
        ;

        String email = usersRequest.getEmail();
        String password = usersRequest.getPassword();

        LoginRequest loginRequest = LoginDataFactory.login(email, password);

        token = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .path("authorization")
        ;

        ProductsRequest productsRequest = ProductsDataFactory.createProduct();

        idProduct = produtctsClient.productsPost(token, productsRequest)
                .then()
                    .extract()
                        .path("_id")
        ;

        quantityProduct = produtctsClient.productsIdGet(idProduct)
                .then()
                    .extract()
                        .path("quantidade")
        ;
    }

    @BeforeEach
    public void clearCart() { cartClient.cartDeleteCancel(token); }

    @AfterAll
    public static void tearDown() {

        produtctsClient.productsDelete(token, idProduct);
        usersClient.usersDelete(idUser);
        cartClient.cartDeleteCancel(token);
    }

    @Test
    @DisplayName("CE001 - Validate cart create sucessfully")
    @Tag("functional")
    public void testCreateCartSucessfully() {

        cartRequest = CartDataFactory.createCart(idProduct, quantityProduct);

        Response response = cartClient.cartPost(token, cartRequest)
                .then()
                    .extract()
                       .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_CREATED, cartResponse.getStatusCode()),
                () -> Assertions.assertEquals(CartMessage.CREATED_SUCESS, cartResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE002 - Attempt to validate cart create with incorrect id")
    @Tag("functional")
    public void testCreateCartIncorrectId() {

        cartRequest = CartDataFactory.createCartIncorrectId(BaseDataFactory.name(), quantityProduct);

        Response response = cartClient.cartPost(token, cartRequest)
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, cartResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.PRODUCT_NOT_FOUND, cartResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate cart create with incorrect quantity")
    @Tag("functional")
    public void testCreateCartIncorrectQuantity() {

        cartRequest = CartDataFactory.createCartIncorrectQuantity(idProduct, quantityProduct);

        Response response = cartClient.cartPost(token, cartRequest)
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, cartResponse.getStatusCode()),
                () -> Assertions.assertEquals(CartMessage.INSUFFICIENT_QUANTITY, cartResponse.getMessage())
        );
    }
}
