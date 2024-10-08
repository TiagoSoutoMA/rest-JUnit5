package br.com.rest.tests.users;

import br.com.rest.client.UsersClient;
import br.com.rest.data.factory.BaseDataFactory;
import br.com.rest.data.factory.UsersDataFactory;
import br.com.rest.model.request.UsersRequest;
import br.com.rest.model.response.UsersResponse;
import br.com.rest.utils.messages.UsersMessage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PutUser")
public class PutUsersTest {

    UsersClient usersClient = new UsersClient();
    UsersRequest usersRequest;
    UsersResponse usersResponse;
    private String id;

    @BeforeAll
    public void setUp() {

        usersRequest = UsersDataFactory.createUser();

        id = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .path("_id")
        ;
    }

    @AfterAll
    public void tearDown() { usersClient.usersDelete(id); }

    @Test
    @DisplayName("CE001 - Validate edit user successfully")
    public void testEdiUserSucessfully() {

        usersRequest = UsersDataFactory.createUser();

        Response response = usersClient.usersPut(id, usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.RECORD_CHANGED, usersResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE002 - Attempt validate edit user with incorrect id")
    public void testEdiUserWithIncorrectId() {

        usersRequest = UsersDataFactory.createUser();

        Response response = usersClient.usersPut(BaseDataFactory.name(), usersRequest)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());
        String userId = usersResponse.get_id();

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_CREATED, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.SUCESS, usersResponse.getMessage()),
                () -> Assertions.assertNotNull(usersResponse.get_id())
        );

        usersClient.usersDelete(userId);
    }

    @Test
    @DisplayName("CE003 - Attempt validate edit user with empty credentials")
    public void testEdiUserWithEmptyCredentials() {

        usersRequest = UsersDataFactory.createUserWithEmptyCredentials();

        Response response = usersClient.usersPut(id, usersRequest)
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
    @DisplayName("CE004 - Attempt validate edit user with empty name")
    public void testEdiUserWithEmptyName() {

        usersRequest = UsersDataFactory.createUserWithEmptyName();

        Response response = usersClient.usersPut(id, usersRequest)
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
    @DisplayName("CE005 - Attempt validate edit user with empty email")
    @Tag("functional")
    public void testEdiUserWithEmptyEmail() {

        usersRequest = UsersDataFactory.createUserWithEmptyEmail();

        Response response = usersClient.usersPut(id, usersRequest)
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
    @DisplayName("CE006 - Attempt validate edit user with empty password")
    @Tag("functional")
    public void testEdiUserWithEmptyPassword() {

        usersRequest = UsersDataFactory.createUserWithEmptyPassword();

        Response response = usersClient.usersPut(id, usersRequest)
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
    @DisplayName("CE007 - Attempt validate edit user with empty administrator")
    @Tag("functional")
    public void testEdiUserWithEmptyAdministrator() {

        usersRequest = UsersDataFactory.createUserWithEmptyAdministrator();

        Response response = usersClient.usersPut(id, usersRequest)
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
    @DisplayName("CE008 - Attempt validate edit user with invalid administrator")
    @Tag("functional")
    public void testEdiUserWithInvalidAdministrator() {

        usersRequest = UsersDataFactory.createUserWithInvalidAdministrator();

        Response response = usersClient.usersPut(id, usersRequest)
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
    @DisplayName("CE009 - Attempt validate edit user with invalid email")
    @Tag("functional")
    public void testEdiUserWithInvalidEmail() {

        usersRequest = UsersDataFactory.createUserWithInvalidEmail();

        Response response = usersClient.usersPut(id, usersRequest)
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
    @DisplayName("CE010 - Attempt validate edit user with duplicated email")
    @Tag("functional")
    public void testEdiUserWithDuplicatedEmail() {

        usersRequest = UsersDataFactory.createUserWithDuplicatedEmail();

        Response response = usersClient.usersPut(id, usersRequest)
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
    @DisplayName("Validade edit contract")
    public void testEditContract() {

        usersRequest = UsersDataFactory.createUser();

        usersClient.usersPut(id, usersRequest)
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/putUsersSchema.json"))
        ;
    }
}
