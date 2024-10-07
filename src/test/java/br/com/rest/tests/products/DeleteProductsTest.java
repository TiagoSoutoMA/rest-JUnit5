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

@DisplayName("DeleteProducts")
public class DeleteProductsTest {

    private static ProdutctsClient produtctsClient = new ProdutctsClient();
    private static LoginClient loginClient = new LoginClient();
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

        ProductsRequest produtoRequest = ProductsDataFactory.createProduct();

        idProduto = produtctsClient.productsPost(token, produtoRequest)
                .then()
                    .extract()
                        .path("_id")
        ;
    }

    @Test
    @DisplayName("CE001 - Validate product deletion successfully")
    @Tag("functional")
    public void testDeleteProductSucessfully() {

        Response response = produtctsClient.productsDelete(token, idProduto)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.EXCLUDED_SUCESS, productsResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE002 - Attempt to validate the deletion of a product that is part of the cart")
    @Tag("functional")
    public void testDeleteProductIsPartCart() {

        Response response = produtctsClient.productsDelete(token, ProductsDataFactory.idProductCart())
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.PRODUCT_PART_CART, productsResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate the deletion of a product with invalid id")
    @Tag("functional")
    public void testDeleteProductInvalidId() {

        Response response = produtctsClient.productsDelete(token, ProductsDataFactory.name())
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, productsResponse.getStatusCode()),
                () -> Assertions.assertEquals(ProductsMessage.PRODUCT_NOT_EXCLUDED, productsResponse.getMessage())
        );
    }
}
