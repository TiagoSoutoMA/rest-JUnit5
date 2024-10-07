package br.com.rest.client;

import br.com.rest.model.request.CartRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class CartClient extends BaseClient {

    private final String CART = "/carrinhos";
    private final String CART_ID = CART + "/{id}";
    private final String CART_CONCLUDE = CART + "/concluir-compra";
    private final String CART_CANCEL = CART + "/cancelar-compra";

    public Response cartGet() {

        return step("Sending request GET to: " + CART, () ->

                given()
                        .spec(set())
                .when()
                        .get(CART)
        );
    }

    public Response cartIdGet(String id) {

        return step("Sending request GET to: " + CART_ID, () ->

                given()
                        .spec(set())
                        .pathParam("id", id)
                .when()
                        .get(CART_ID)
        );
    }

    public Response cartPost(String token, CartRequest cartRequest) {

        return step("Sending request POST to: " + CART, () ->

                given()
                        .spec(set())
                        .header("Authorization", token)
                        .contentType(ContentType.JSON)
                        .body(cartRequest)
                .when()
                        .post(CART)
        );
    }

    public Response cartDeleteConclude(String token) {

        return step("Sending request DELETE to: " + CART_CONCLUDE, () ->

                given()
                        .spec(set())
                        .header("Authorization", token)
                .when()
                        .delete(CART_CONCLUDE)
        );
    }

    public Response cartDeleteCancel(String token) {

        return step("Sending request DELETE to: " + CART_CANCEL, () ->

                given()
                        .spec(set())
                        .header("Authorization", token)
                .when()
                        .delete(CART_CANCEL)
        );
    }
}
