package br.com.rest.tests.products;

import br.com.rest.client.LoginClient;
import br.com.rest.client.ProdutctsClient;
import br.com.rest.data.factory.LoginDataFactory;
import br.com.rest.data.factory.ProductsDataFactory;
import br.com.rest.model.request.LoginRequest;
import br.com.rest.model.request.ProductsRequest;
import br.com.rest.model.response.ProductsResponse;
import br.com.rest.utils.messages.ProductsMessage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

@DisplayName("PostProducts")
public class PostProductsTest {

    private static ProdutctsClient produtctsClient = new ProdutctsClient();
    private static LoginClient loginClient = new LoginClient();
    private ProductsRequest productsRequest;
    private ProductsResponse productsResponse;
    private static String idProduto, token;

    @BeforeAll
    public static void setUp() {

        LoginRequest loginRequest = LoginDataFactory.loginAdmin();

        token = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .path("authorization")
        ;
    }

    @AfterAll
    public static void tearDown() { produtctsClient.productsDelete(token, idProduto); }

    @Test
    @DisplayName("CE001 - Validate product registration sucessfully")
    @Tag("functional")
    public void testRegisterProductSucessfully() {

        productsRequest = ProductsDataFactory.createProduct();

        Response response =produtctsClient.productsPost(token, productsRequest)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());
        idProduto = productsResponse.get_id();

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_CREATED, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.CREATED_SUCESS, productsResponse.getMessage()),
                () -> Assertions.assertNotNull(productsResponse.get_id())
        );
    }

    @Test
    @DisplayName("CE002 - Attempt to validate product registration with invalid data")
    @Tag("functional")
    public void testRegisterProductInvalidData() {

        productsRequest = ProductsDataFactory.createProductWithInvalidData();

        Response response =produtctsClient.productsPost(token, productsRequest)
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
    @DisplayName("CE003 - Attempt to validate product registration with empty name")
    @Tag("functional")
    public void testRegisterProductEmptyName() {

        productsRequest = ProductsDataFactory.createProductWithEmptyName();

        Response response =produtctsClient.productsPost(token, productsRequest)
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
    @DisplayName("CE004 - Attempt to validate product registration with invalid price")
    @Tag("functional")
    public void testRegisterProductInvalidPrice() {

        productsRequest = ProductsDataFactory.createProductWithInvalidPrice();

        Response response =produtctsClient.productsPost(token, productsRequest)
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
    @DisplayName("CE005 - Attempt to validate product registration with empty description")
    @Tag("functional")
    public void testRegisterProductEmptyDescription() {

        productsRequest = ProductsDataFactory.createProductWithEmptyDescription();

        Response response =produtctsClient.productsPost(token, productsRequest)
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
    @DisplayName("CE006 - Attempt to validate product registration with invalid quantity")
    @Tag("functional")
    public void testRegisterProductInvalidQuantity() {

        productsRequest = ProductsDataFactory.createProductWithInvalidQuantity();

        Response response =produtctsClient.productsPost(token, productsRequest)
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
    @DisplayName("CE007 - Attempt to validate product registration with duplicated name")
    @Tag("functional")
    public void testRegisterProductDuplicatedName() {

        productsRequest = ProductsDataFactory.createProductWithDuplicatedName();

        Response response =produtctsClient.productsPost(token, productsRequest)
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
