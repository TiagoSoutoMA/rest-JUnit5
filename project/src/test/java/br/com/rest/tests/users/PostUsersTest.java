package br.com.rest.tests.users;

import br.com.rest.client.UsersClient;
import br.com.rest.data.factory.UsersDataFactory;
import br.com.rest.model.request.UsersRequest;
import br.com.rest.utils.messages.UsersMessage;
import br.com.rest.model.response.UsersResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("PostUser")
public class PostUsersTest {


    UsersClient usersClient = new UsersClient();
    UsersRequest usersRequest;
    UsersResponse usersResponse;
    String id;

    @Test
    @DisplayName("CE001 - Validate register user successfully")
    @Tags({@Tag("functional"), @Tag("health_check")})
    public void testRegisterSucess() {

        usersRequest = UsersDataFactory.createUser();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());
        id = usersResponse.get_id();

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_CREATED, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.SUCESS, usersResponse.getMessage()),
                () -> Assertions.assertNotNull(usersResponse.get_id())
        );

        usersClient.usersDelete(id);
    }

    @Test
    @DisplayName("CE002 - Attempt to validate user registration with empty credentials")
    @Tag("functional")
    public void testRegisterWithEmptyCredentials() {

        usersRequest = UsersDataFactory.createUserWithEmptyCredentials();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.EMPTY_NAME, usersResponse.getNome()),
                () -> Assertions.assertEquals(UsersMessage.EMPTY_EMAIL, usersResponse.getEmail()),
                () -> Assertions.assertEquals(UsersMessage.EMPTY_PASSWORD, usersResponse.getPassword()),
                () -> Assertions.assertEquals(UsersMessage.INCORRECT_ADMINISTRATOR, usersResponse.getAdministrador())
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate user registration with empty name")
    @Tag("functional")
    public void testRegisterWithEmptyName() {

        usersRequest = UsersDataFactory.createUserWithEmptyName();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.EMPTY_NAME, usersResponse.getNome())
        );
    }

    @Test
    @DisplayName("CE004 - Attempt to validate user registration with empty email")
    @Tag("functional")
    public void testRegisterWithEmptyEmail() {

        usersRequest = UsersDataFactory.createUserWithEmptyEmail();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.EMPTY_EMAIL, usersResponse.getEmail())
        );
    }

    @Test
    @DisplayName("CE005 - Attempt to validate user registration with empty password")
    @Tag("functional")
    public void testRegisterWithEmptyPassword() {

        usersRequest = UsersDataFactory.createUserWithEmptyPassword();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.EMPTY_PASSWORD, usersResponse.getPassword())
        );
    }

    @Test
    @DisplayName("CE006 - Attempt to validate user registration with empty administrator")
    @Tag("functional")
    public void testRegisterWithEmptyAdministrator() {

        usersRequest = UsersDataFactory.createUserWithEmptyAdministrator();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.INCORRECT_ADMINISTRATOR, usersResponse.getAdministrador())
        );
    }

    @Test
    @DisplayName("CE007 - Attempt to validate user registration with invalid administrator")
    @Tag("functional")
    public void testRegisterWithInvalidAdministrator() {

        usersRequest = UsersDataFactory.createUserWithInvalidAdministrator();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.INCORRECT_ADMINISTRATOR, usersResponse.getAdministrador())
        );
    }

    @Test
    @DisplayName("CE008 - Attempt to validate user registration with invalid email")
    @Tag("functional")
    public void testRegisterWithInvalidEmail() {

        usersRequest = UsersDataFactory.createUserWithInvalidEmail();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.INVALID_EMAIL, usersResponse.getEmail())
        );
    }

    @Test
    @DisplayName("CE009 - Attempt to validate user registration with duplicated email")
    @Tag("functional")
    public void testRegisterWithDuplicatedEmail() {

        usersRequest = UsersDataFactory.createUserWithDuplicatedEmail();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.DUPLICATED_EMAIL, usersResponse.getMessage())
        );
    }

    @Test
    @DisplayName("Validate register contract")
    @Tag("contract")
    public void testRegisterContract() {

        usersRequest = UsersDataFactory.createUser();

        Response response = usersClient.usersPost(usersRequest)
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/postUsersSchema.json"))
                        .extract()
                            .response()
        ;

        usersClient.usersDelete(response.getBody().path("_id"));
    }
}
