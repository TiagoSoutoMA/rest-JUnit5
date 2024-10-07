package br.com.rest.tests.login;

import br.com.rest.client.LoginClient;
import br.com.rest.data.factory.LoginDataFactory;
import br.com.rest.model.request.LoginRequest;
import br.com.rest.model.response.LoginResponse;
import br.com.rest.utils.messages.LoginMessage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("PostLogin")
public class PostLoginTest {

    LoginClient loginClient = new LoginClient();
    LoginResponse loginResponse;
    LoginRequest loginRequest;

    @Test
    @DisplayName("CE001 - Validate login successfully")
    @Tag("functional")
    public void testLoginSucess() {

        loginRequest = LoginDataFactory.loginAdmin();

        Response response = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .response()
        ;

        loginResponse = response.getBody().as(LoginResponse.class);
        loginResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, loginResponse.getStatusCode()),
                () -> Assertions.assertEquals(LoginMessage.SUCESS, loginResponse.getMessage()),
                () -> Assertions.assertNotNull(loginResponse.getAuthorization())
        );
    }

    @Test
    @DisplayName("CE002 - Attempt to validate login with unregistered credentials")
    @Tag("functional")
    public void testLoginCredentialsNotRegistered() {

        loginRequest = LoginDataFactory.loginCredentialsNotRegistered();

        Response response = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .response()
        ;

        loginResponse = response.getBody().as(LoginResponse.class);
        loginResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_UNAUTHORIZED, loginResponse.getStatusCode()),
                () -> Assertions.assertEquals(LoginMessage.CREDENTIALS_NOT_REGISTERED, loginResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate login with invalid email format")
    @Tag("functional")
    public void testLoginInvalidEmail() {

        loginRequest = LoginDataFactory.loginInvalidEmail();

        Response response = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .response()
        ;

        loginResponse = response.getBody().as(LoginResponse.class);
        loginResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, loginResponse.getStatusCode()),
                () -> Assertions.assertEquals(LoginMessage.INVALID_EMAIL_FORMAT, loginResponse.getEmail())
        );
    }

    @Test
    @DisplayName("CE004 - Attempt to validate login with empty email")
    @Tag("functional")
    public void testLoginEmptyEmail() {

        loginRequest = LoginDataFactory.loginEmptyEmail();

        Response response = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .response()
        ;

        loginResponse = response.getBody().as(LoginResponse.class);
        loginResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, loginResponse.getStatusCode()),
                () -> Assertions.assertEquals(LoginMessage.EMPTY_EMAIL, loginResponse.getEmail())
        );
    }

    @Test
    @DisplayName("CE005 - Attempt to validate login with empty password")
    @Tag("functional")
    public void testLoginEmptyPassword() {

        loginRequest = LoginDataFactory.loginEmptyPassword();

        Response response = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .response()
        ;

        loginResponse = response.getBody().as(LoginResponse.class);
        loginResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, loginResponse.getStatusCode()),
                () -> Assertions.assertEquals(LoginMessage.EMPTY_PASSWORD, loginResponse.getPassword())
        );
    }

    @Test
    @DisplayName("CE006 - Attempt to validate login with empty credentials")
    @Tag("functional")
    public void testLoginEmptyCredentials() {

        loginRequest = LoginDataFactory.loginEmptyCredentials();

        Response response = loginClient.loginPost(loginRequest)
                .then()
                    .extract()
                        .response()
        ;

        loginResponse = response.getBody().as(LoginResponse.class);
        loginResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, loginResponse.getStatusCode()),
                () -> Assertions.assertEquals(LoginMessage.EMPTY_EMAIL, loginResponse.getEmail()),
                () -> Assertions.assertEquals(LoginMessage.EMPTY_PASSWORD, loginResponse.getPassword())
        );
    }

    @Test
    @DisplayName("Validate login contract")
    @Tag("contract")
    public void testLoginContract() {

        loginRequest = LoginDataFactory.loginAdmin();

        loginClient.loginPost(loginRequest)
                .then()
                   .body(matchesJsonSchemaInClasspath("schemas/postLoginSchema.json"))
        ;
    }
}
