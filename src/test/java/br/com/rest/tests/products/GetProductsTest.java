package br.com.rest.tests.products;

import br.com.rest.client.LoginClient;
import br.com.rest.client.ProdutctsClient;
import br.com.rest.data.factory.BaseDataFactory;
import br.com.rest.data.factory.LoginDataFactory;
import br.com.rest.data.factory.ProductsDataFactory;
import br.com.rest.model.request.LoginRequest;
import br.com.rest.model.request.ProductsRequest;
import br.com.rest.model.response.ProductsResponse;
import br.com.rest.utils.messages.ProductsMessage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("GetProducts")
public class GetProductsTest {

    private static ProdutctsClient produtctsClient = new ProdutctsClient();
    private static LoginClient loginClient = new LoginClient();
    private ProductsResponse productsResponse;
    private static String idProduct, token;

    @BeforeAll
    public static void setUp() {

        LoginRequest loginRequest = LoginDataFactory.loginAdmin();

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
    }

    @AfterAll
    public static void tearDown() { produtctsClient.productsDelete(token, idProduct); }

    @Test
    @DisplayName("CE001 - Validate products list sucessfully")
    @Tag("functional")
    public void testListProductsSucessfully() {

        Response response = produtctsClient.productsGet()
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, productsResponse.getStatusCode(), "Status code deve ser 200"),
                () -> Assertions.assertFalse(productsResponse.getProdutos().isEmpty(), "A lista de produtos não deve ser vazia"),
                () -> Assertions.assertEquals(Integer.parseInt(productsResponse.getQuantidade()), productsResponse.getProdutos().size(), "A quantidade deve ser igual ao tamanho da lista"),
                () -> Assertions.assertTrue(
                        productsResponse.getProdutos().stream().allMatch(product ->
                                product.getNome() != null && !product.getNome().isEmpty() &&
                                product.getPreco() != null &&
                                product.getDescricao() != null && !product.getDescricao().isEmpty() &&
                                product.getQuantidade() != null &&
                                product.get_id() != null && !product.get_id().isEmpty()
                        ), "Todos os produtos devem ter nome, preco, descrição e quantidade preenchidos e um id válido"
                )
        );
    }

    @Test
    @DisplayName("CE002 - Validate products listing by id sucessfully")
    @Tag("functional")
    public void testListProductsByIdSucessfully() {

        Response response = produtctsClient.productsIdGet(idProduct)
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, productsResponse.getStatusCode(), "Status code deve ser 200"),
                () -> Assertions.assertTrue(productsResponse.getNome() != null && !productsResponse.getNome().isEmpty()),
                () -> Assertions.assertNotNull(productsResponse.getPreco()),
                () -> Assertions.assertTrue(productsResponse.getDescricao() != null && !productsResponse.getDescricao().isEmpty()),
                () -> Assertions.assertNotNull(Integer.parseInt(productsResponse.getQuantidade())),
                () -> Assertions.assertTrue(productsResponse.get_id() != null && !productsResponse.get_id().isEmpty())
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate products list with empty id")
    @Tag("functional")
    public void testListProductsEmptyId() {

        Response response = produtctsClient.productsIdGet(BaseDataFactory.empty())
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, productsResponse.getStatusCode(), "Status code deve ser 200"),
                () -> Assertions.assertFalse(productsResponse.getProdutos().isEmpty(), "A lista de produtos não deve ser vazia"),
                () -> Assertions.assertEquals(Integer.parseInt(productsResponse.getQuantidade()), productsResponse.getProdutos().size(), "A quantidade deve ser igual ao tamanho da lista"),
                () -> Assertions.assertTrue(
                        productsResponse.getProdutos().stream().allMatch(product ->
                                product.getNome() != null && !product.getNome().isEmpty() &&
                                        product.getPreco() != null &&
                                        product.getDescricao() != null && !product.getDescricao().isEmpty() &&
                                        product.getQuantidade() != null &&
                                        product.get_id() != null && !product.get_id().isEmpty()
                        ), "Todos os produtos devem ter nome, preco, descrição e quantidade preenchidos e um id válido"
                )
        );
    }

    @Test
    @DisplayName("CE004 - Attempt to validate products list with invalid id")
    @Tag("functional")
    public void testListProductsInvalidId() {

        Response response = produtctsClient.productsIdGet(BaseDataFactory.name())
                .then()
                    .extract()
                        .response()
        ;

        productsResponse = response.getBody().as(ProductsResponse.class);
        productsResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, productsResponse.getStatusCode(), "Status code deve ser 400"),
                () -> Assertions.assertEquals(ProductsMessage.PRODUCT_NOT_FOUND, productsResponse.getMessage())
        );
    }

    @Test
    @DisplayName("Validate get products contract")
    @Tag("contract")
    public void testGetProductsContract() {

        produtctsClient.productsGet()
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/getProductsSchema.json"))
        ;
    }
}
