package br.com.rest.tests.products;

import br.com.rest.client.LoginClient;
import br.com.rest.client.ProdutctsClient;
import br.com.rest.client.UsersClient;
import br.com.rest.data.factory.BaseDataFactory;
import br.com.rest.data.factory.LoginDataFactory;
import br.com.rest.data.factory.ProductsDataFactory;
import br.com.rest.data.factory.UsersDataFactory;
import br.com.rest.model.request.LoginRequest;
import br.com.rest.model.request.ProductsRequest;
import br.com.rest.model.request.UsersRequest;
import br.com.rest.model.response.ProductsResponse;
import br.com.rest.utils.messages.ProductsMessage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

@DisplayName("PutProducts")
public class PutProductsTest {

    private static ProdutctsClient produtctsClient = new ProdutctsClient();
    private static LoginClient loginClient = new LoginClient();
    private static final UsersClient usersClient = new UsersClient();
    private static ProductsRequest productsRequest;
    private ProductsResponse productsResponse;
    private static String idProduto, token;

    @BeforeAll
    public static void setUp() {

        UsersRequest usersRequest = UsersDataFactory.createUserAdmin();

        usersClient.usersPost(usersRequest);

        LoginRequest loginRequest = LoginDataFactory.login(usersRequest.getEmail(), usersRequest.getPassword());


        token = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .path("authorization")
        ;

        productsRequest = ProductsDataFactory.createProduct();

        idProduto = produtctsClient.productsPost(token, productsRequest)
                .then()
                    .extract()
                        .path("_id")
        ;
    }

    @AfterAll
    public static void tearDown() { produtctsClient.productsDelete(token, idProduto); }

    @Test
    @DisplayName("CE001 - Validate product update sucessfully")
    @Tag("functional")
    public void testUpdateProductSucessfully() {

        productsRequest = ProductsDataFactory.createProduct();

        Response response = produtctsClient.productsPut(token, idProduto, productsRequest)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.UPDATE_SUCESS, productsResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE002 - Attempt to validate product update with incorrect id")
    @Tag("functional")
    public void testUpdateProductIncorrectId() {

        productsRequest = ProductsDataFactory.createProduct();

        Response response = produtctsClient.productsPut(token, BaseDataFactory.name(), productsRequest)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_CREATED, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.CREATED_SUCESS, productsResponse.getMessage()),
                () -> Assertions.assertNotNull(productsResponse.get_id())
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate product update with invalid data")
    @Tag("functional")
    public void testUpdateProductInvalidData() {

        productsRequest = ProductsDataFactory.createProductWithInvalidData();

        Response response = produtctsClient.productsPut(token, idProduto, productsRequest)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.EMPTY_NAME, productsResponse.getNome()),
                () -> Assertions.assertEquals(ProductsMessage.INVALID_PRICE, productsResponse.getPreco()),
                () -> Assertions.assertEquals(ProductsMessage.EMPTY_DESCRIPTION, productsResponse.getDescricao()),
                () -> Assertions.assertEquals(ProductsMessage.INVALID_QUANTITY, productsResponse.getQuantidade())
        );
    }

    @Test
    @DisplayName("CE004 - Attempt to validate product update with empty name")
    @Tag("functional")
    public void testUpdateProductEmptyName() {

        productsRequest = ProductsDataFactory.createProductWithEmptyName();

        Response response = produtctsClient.productsPut(token, idProduto, productsRequest)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.EMPTY_NAME, productsResponse.getNome())
        );
    }

    @Test
    @DisplayName("CE005 - Attempt to validate product update with invalid price")
    @Tag("functional")
    public void testUpdateProductInvalidPrice() {

        productsRequest = ProductsDataFactory.createProductWithInvalidPrice();

        Response response = produtctsClient.productsPut(token, idProduto, productsRequest)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.INVALID_PRICE, productsResponse.getPreco())
        );
    }

    @Test
    @DisplayName("CE006 - Attempt to validate product update with empty description")
    @Tag("functional")
    public void testUpdateProductEmptyDescription() {

        productsRequest = ProductsDataFactory.createProductWithEmptyDescription();

        Response response = produtctsClient.productsPut(token, idProduto, productsRequest)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.EMPTY_DESCRIPTION, productsResponse.getDescricao())
        );
    }

    @Test
    @DisplayName("CE007 - Attempt to validate product update with invalid quantity")
    @Tag("functional")
    public void testUpdateProductInvalidQuantity() {

        productsRequest = ProductsDataFactory.createProductWithInvalidQuantity();

        Response response = produtctsClient.productsPut(token, idProduto, productsRequest)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.INVALID_QUANTITY, productsResponse.getQuantidade())
        );
    }

    @Test
    @DisplayName("CE008 - Attempt to validate product update with duplicated name")
    @Tag("functional")
    public void testUpdateProductDuplicatedName() {

        productsRequest = ProductsDataFactory.createProductWithDuplicatedName();

        Response response = produtctsClient.productsPut(token, idProduto, productsRequest)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.DUPLICATED_NAME, productsResponse.getMessage())
        );
    }
}
