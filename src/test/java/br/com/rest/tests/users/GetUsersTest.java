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

@DisplayName("GetUser")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetUsersTest {

    UsersClient usersClient = new UsersClient();
    UsersResponse usersResponse;
    private String id;

    @BeforeAll
    public void setUp() {

        UsersRequest usersRequest = UsersDataFactory.createUser();

        id = usersClient.usersPost(usersRequest)
                .then()
                    .extract()
                        .path("_id")
        ;
    }

    @AfterAll
    public void tearDown() { usersClient.usersDelete(id); }

    @Test
    @DisplayName("CE001 - Validate users list sucessfully")
    @Tag("functional")
    public void testListUsersSucessfully() {

        Response response = usersClient.usersGet()
                .then()
                   .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, usersResponse.getStatusCode(), "Status code deve ser 200"),
                () -> Assertions.assertFalse(usersResponse.getUsuarios().isEmpty(), "A lista de usuários não deve ser vazia"),
                () -> Assertions.assertEquals(usersResponse.getQuantidade(), usersResponse.getUsuarios().size(), "A quantidade deve ser igual ao tamanho da lista"),
                () -> Assertions.assertTrue(
                        usersResponse.getUsuarios().stream().allMatch(user ->
                                user.getNome() != null && !user.getNome().isEmpty() &&
                                user.getEmail() != null && !user.getEmail().isEmpty() &&
                                user.getPassword() != null && !user.getPassword().isEmpty() &&
                                user.getAdministrador() != null && !user.getAdministrador().isEmpty() &&
                                user.get_id() != null && !user.get_id().isEmpty()
                        ), "Todos os usuários devem ter nome, email, password e administrador preenchidos e um id válido"
                )
        );
    }

    @Test
    @DisplayName("CE002 - Validate user listing by id sucessfully")
    @Tag("functional")
    public void testListUsersByIdSucessfully() {

        Response response = usersClient.usersIdGet(id)
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, usersResponse.getStatusCode(), "Status code deve ser 200"),
                () -> Assertions.assertTrue(usersResponse.getNome() != null && !usersResponse.getNome().isEmpty()),
                () -> Assertions.assertTrue(usersResponse.getEmail() != null && !usersResponse.getEmail().isEmpty()),
                () -> Assertions.assertTrue(usersResponse.getPassword() != null && !usersResponse.getPassword().isEmpty()),
                () -> Assertions.assertTrue(usersResponse.getAdministrador() != null && !usersResponse.getAdministrador().isEmpty()),
                () -> Assertions.assertTrue(usersResponse.get_id() != null && !usersResponse.get_id().isEmpty())
        );
    }

    @Test
    @DisplayName("CE003 - Attempt to validate user listing with empty id")
    @Tag("functional")
    public void testListUsersWithEmptyId() {

        Response response = usersClient.usersIdGet(BaseDataFactory.empty())
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_OK, usersResponse.getStatusCode(), "Status code deve ser 200"),
                () -> Assertions.assertFalse(usersResponse.getUsuarios().isEmpty(), "A lista de usuários não deve ser vazia"),
                () -> Assertions.assertEquals(usersResponse.getQuantidade(), usersResponse.getUsuarios().size(), "A quantidade deve ser igual ao tamanho da lista"),
                () -> Assertions.assertTrue(
                        usersResponse.getUsuarios().stream().allMatch(user ->
                                user.getNome() != null && !user.getNome().isEmpty() &&
                                user.getEmail() != null && !user.getEmail().isEmpty() &&
                                user.getPassword() != null && !user.getPassword().isEmpty() &&
                                user.getAdministrador() != null && !user.getAdministrador().isEmpty() &&
                                user.get_id() != null && !user.get_id().isEmpty()
                        ), "Todos os usuários devem ter nome, email, password e administrador preenchidos e um id válido"
                )
        );
    }

    @Test
    @DisplayName("CE004 - Attempt to validate user listing with incorrect id")
    @Tag("functional")
    public void testListUsersWithInvalidId() {

        Response response = usersClient.usersIdGet(BaseDataFactory.name())
                .then()
                    .extract()
                        .response()
        ;

        usersResponse = response.getBody().as(UsersResponse.class);
        usersResponse.setStatusCode(response.getStatusCode());

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, usersResponse.getStatusCode(), "Status code deve ser 400"),
                () -> Assertions.assertEquals(UsersMessage.USER_NOT_FOUND, usersResponse.getMessage())
        );
    }

    @Test
    @DisplayName("Validate get users contract")
    @Tag("contract")
    public void testGetUsersContract() {

        usersClient.usersGet()
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/getUsersSchema.json"))
        ;
    }
}
