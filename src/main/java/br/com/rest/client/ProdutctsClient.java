package br.com.rest.client;

import br.com.rest.model.request.ProductsRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class ProdutctsClient extends BaseClient {

    private final String PRODUCTS = "/produtos";
    private final String PRODUCTS_ID = PRODUCTS + "/{id}";

    public Response productsPost(String token, ProductsRequest productsRequest) {

        return step("Sending request POST to: " + PRODUCTS, () ->

                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .header("Authorization", token)
                        .body(productsRequest)
                .when()
                        .post(PRODUCTS)
        );
    }

    public Response productsGet() {

        return step("Sending request GET to: " + PRODUCTS, () ->

                given()
                        .spec(super.set())
                .when()
                        .get(PRODUCTS)
        );
    }

    public Response productsIdGet(String id) {

        return step("Sending request GET to: " + PRODUCTS_ID, () ->

                given()
                        .spec(super.set())
                        .pathParam("id", id)
                .when()
                        .get(PRODUCTS_ID)
        );
    }

    public  Response productsPut(String token, String id, ProductsRequest productsRequest) {

        return step("Sending request PUT to: " + PRODUCTS_ID, () ->

                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .header("Authorization", token)
                        .pathParam("id", id)
                        .body(productsRequest)
                .when()
                        .put(PRODUCTS_ID)
        );
    }

    public Response productsDelete(String token, String id) {

        return step("Sending request DELETE to: " + PRODUCTS_ID, () ->

                given()
                        .spec(super.set())
                        .header("Authorization", token)
                        .pathParam("id", id)
                .when()
                        .delete(PRODUCTS_ID)
        );
    }
}
