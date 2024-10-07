package br.com.rest.client;

import br.com.rest.model.request.LoginRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class LoginClient extends BaseClient {

    public final String LOGIN = "/login";

    public Response loginPost(LoginRequest loginBody) {

        return step("Sending request POST to: " + LOGIN, () ->

                given()
                        .spec(set())
                        .contentType(ContentType.JSON)
                        .body(loginBody)
                .when()
                        .post(LOGIN)
        );
    }
}
