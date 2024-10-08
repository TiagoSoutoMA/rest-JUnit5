package br.com.rest.client;

import br.com.rest.model.request.UsersRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class UsersClient extends BaseClient {

    private final String USERS = "/usuarios";
    private final String USERS_ID = USERS + "/{id}";

    public Response usersGet() {

        return step("Sending request GET to: " + USERS, () ->

                given()
                        .spec(set())
                        .contentType(ContentType.JSON)
                .when()
                        .get(USERS)
        );
    }

    public Response usersIdGet(String id) {

        return step("Sending request GET to: " + USERS_ID, () ->

                given()
                        .spec(set())
                        .contentType(ContentType.JSON)
                        .pathParam("id", id)
                .when()
                        .get(USERS_ID)
        );
    }

    public Response usersPost(UsersRequest usersRequest) {

        return step("Sending request POST to: " + USERS, () ->

                given()
                        .spec(set())
                        .contentType(ContentType.JSON)
                        .body(usersRequest)
                .when()
                        .post(USERS)
        );
    }

    public Response usersPut(String id, UsersRequest usersRequest) {

        return step("Sending request PUT to: " + USERS, () ->

                given()
                        .spec(set())
                        .contentType(ContentType.JSON)
                        .pathParam("id", id)
                        .body(usersRequest)
                .when()
                        .put(USERS_ID)
        );
    }

    public Response usersDelete(String id) {

        return step("Sending request DELETE to: " + USERS, () ->

                given()
                        .spec(set())
                        .pathParam("id", id)
                .when()
                        .delete(USERS_ID)
        );
    }
}
