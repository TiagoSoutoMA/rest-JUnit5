package br.com.rest.tests.users;

import br.com.rest.client.UsersClient;
import br.com.rest.data.factory.UsersDataFactory;
import br.com.rest.model.request.UsersRequest;
import br.com.rest.model.response.UsersResponse;
import br.com.rest.utils.messages.UsersMessage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DeleteUser")
public class DeleteUsersTest {

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

    @Test
    @DisplayName("CE001 - Validate delete user sucessfully")
    @Tag("functional")
    public void testDeleteUserSucessfully() {

        Response response = usersClient.usersDelete(id)
                .then()
                   .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.DELETE_SUCESS, usersResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE002 - Attempt to validate delete user with incorrect id")
    @Tag("functional")
    public void testDeleteUserIncorrectId() {

        Response response = usersClient.usersDelete(UsersDataFactory.name())
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.DELETE_FAIL, usersResponse.getMessage())
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate delete user with a registered cart")
    @Tag("functional")
    public void testDeleteUserRegisteredCart() {

        Response response = usersClient.usersDelete(UsersDataFactory.idAdmin())
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode()),
                () -> Assertions.assertEquals(UsersMessage.DELETE_FAIL_CART, usersResponse.getMessage()),
                () -> Assertions.assertNotNull(usersResponse.getIdCarrinho())
        );
    }

    @Test
    @DisplayName("Validate delete contract")
    @Tag("contract")
    public void testDeleteContract() {

        usersClient.usersDelete(id)
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/deleteSchema.json"))
        ;
    }
}
