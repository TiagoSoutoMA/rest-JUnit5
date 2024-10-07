package br.com.rest.data.factory;

import br.com.rest.model.request.UsersRequest;
import br.com.rest.utils.Manipulation;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class UsersDataFactory {

    private static Properties prop = Manipulation.getProp();

    public static UsersRequest createUserAdmin() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(DataFaker.name());
        usersRequest.setEmail(DataFaker.email());
        usersRequest.setPassword(DataFaker.password());
        usersRequest.setAdministrador("true");

        return usersRequest;
    }

    public static UsersRequest createUser() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(DataFaker.name());
        usersRequest.setEmail(DataFaker.email());
        usersRequest.setPassword(DataFaker.password());
        usersRequest.setAdministrador(DataFaker.isAdmin());

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyCredentials() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(StringUtils.EMPTY);
        usersRequest.setEmail(StringUtils.EMPTY);
        usersRequest.setPassword(StringUtils.EMPTY);
        usersRequest.setAdministrador(StringUtils.EMPTY);

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyName() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(StringUtils.EMPTY);
        usersRequest.setEmail(DataFaker.email());
        usersRequest.setPassword(DataFaker.password());
        usersRequest.setAdministrador(DataFaker.isAdmin());

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyEmail() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(DataFaker.name());
        usersRequest.setEmail(StringUtils.EMPTY);
        usersRequest.setPassword(DataFaker.password());
        usersRequest.setAdministrador(DataFaker.isAdmin());

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyPassword() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(DataFaker.name());
        usersRequest.setEmail(DataFaker.email());
        usersRequest.setPassword(StringUtils.EMPTY);
        usersRequest.setAdministrador(DataFaker.isAdmin());

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyAdministrator() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(DataFaker.name());
        usersRequest.setEmail(DataFaker.email());
        usersRequest.setPassword(DataFaker.password());
        usersRequest.setAdministrador(StringUtils.EMPTY);

        return usersRequest;
    }

    public static UsersRequest createUserWithInvalidEmail() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(DataFaker.name());
        usersRequest.setEmail(DataFaker.invalidEmail());
        usersRequest.setPassword(DataFaker.password());
        usersRequest.setAdministrador(DataFaker.isAdmin());

        return usersRequest;
    }

    public static UsersRequest createUserWithInvalidAdministrator() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(DataFaker.name());
        usersRequest.setEmail(DataFaker.invalidEmail());
        usersRequest.setPassword(DataFaker.password());
        usersRequest.setAdministrador(DataFaker.name());

        return usersRequest;
    }

    public static UsersRequest createUserWithDuplicatedEmail() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(DataFaker.name());
        usersRequest.setEmail(prop.getProperty("email"));
        usersRequest.setPassword(DataFaker.password());
        usersRequest.setAdministrador(DataFaker.isAdmin());

        return usersRequest;
    }

    public static String empty() { return DataFaker.empty(); }

    public static String name() { return DataFaker.name(); }

    public static String idAdmin() { return prop.getProperty("id"); }
}
