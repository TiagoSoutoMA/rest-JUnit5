package br.com.rest.tests.cart;

import br.com.rest.client.*;
import br.com.rest.data.factory.*;
import br.com.rest.model.request.*;
import br.com.rest.model.response.CartResponse;
import br.com.rest.utils.messages.CartMessage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

@DisplayName("DeleteCart")
public class DeleteCartTest {

    private CartClient cartClient = new CartClient();
    private ProdutctsClient produtctsClient = new ProdutctsClient();
    private LoginClient loginClient = new LoginClient();
    private UsersClient usersClient = new UsersClient();
    private CartRequest cartRequest;
    private ProductsRequest productsRequest;
    private LoginRequest loginRequest;
    private UsersRequest usersRequest;
    private CartResponse cartResponse;
    private String idProduct, idUser, userIdNoCart, token, tokenUserIdNoCart, email, password;
    private Integer quantityProduct;

    @BeforeEach
    public void setUp() {

        usersRequest = UsersDataFactory.createUserAdmin();

        userIdNoCart = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .path("_id")
        ;

        email = usersRequest.getEmail();
        password = usersRequest.getPassword();

        loginRequest = LoginDataFactory.login(email, password);

        tokenUserIdNoCart = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .path("authorization")
        ;

        UsersRequest usersRequest = UsersDataFactory.createUserAdmin();

        idUser = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .path("_id")
        ;

        email = usersRequest.getEmail();
        password = usersRequest.getPassword();

        loginRequest = LoginDataFactory.login(email, password);

        token = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .path("authorization")
        ;

        productsRequest = ProductsDataFactory.createProduct();

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

        cartRequest = CartDataFactory.createCart(idProduct, quantityProduct);

        cartClient.cartPost(token, cartRequest);
    }

    @AfterEach
    public void tearDown() {

        produtctsClient.productsDelete(token, idProduct);
        usersClient.usersDelete(userIdNoCart);
        usersClient.usersDelete(idUser);
    }

    @Test
    @DisplayName("CE001 - Validate complete purchase successfully")
    @Tag("functional")
    public void testCompletePurchaseSucessfully() {

        Response response = cartClient.cartDeleteConclude(token)
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, cartResponse.getStatusCode()),
                () -> Assertions.assertEquals(CartMessage.EXCLUDED_SUCESS, cartResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE002 - Attempt to validate complete purchase no cart")
    @Tag("functional")
    public void testCompletePurchaseNoCart() {

        Response response = cartClient.cartDeleteConclude(tokenUserIdNoCart)
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, cartResponse.getStatusCode()),
                () -> Assertions.assertEquals(CartMessage.USER_CART_NOT_FOUND, cartResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate complete purchase no token")
    @Tag("functional")
    public void testCompletePurchaseNoToken() {

        Response response = cartClient.cartDeleteConclude(CartDataFactory.empty())
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_UNAUTHORIZED, cartResponse.getStatusCode()),
                () -> Assertions.assertEquals(CartMessage.TOKEN_NOT_FOUND_OR_INVALID, cartResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE004 - Validate cancel purchase sucessfully")
    @Tag("functional")
    public void testCancelPurchaseSucessfully() {

        Response response = cartClient.cartDeleteCancel(token)
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, cartResponse.getStatusCode()),
                () -> Assertions.assertEquals(CartMessage.CART_EXCLUDED, cartResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE005 - Attempt to validate cancel purchase no cart")
    @Tag("functional")
    public void testCancelPurchaseNoCart() {

        Response response = cartClient.cartDeleteCancel(tokenUserIdNoCart)
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, cartResponse.getStatusCode()),
                () -> Assertions.assertEquals(CartMessage.USER_CART_NOT_FOUND, cartResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE006 - Attempt to validate cancel purchase no cart")
    @Tag("functional")
    public void testCancelPurchaseNoToken() {

        Response response = cartClient.cartDeleteCancel(CartDataFactory.empty())
                .then()
                    .extract()
                        .response()
        ;

        cartResponse = response.getBody().as(CartResponse.class);
        cartResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_UNAUTHORIZED, cartResponse.getStatusCode()),
                () -> Assertions.assertEquals(CartMessage.TOKEN_NOT_FOUND_OR_INVALID, cartResponse.getMessage())
        );
    }
}
