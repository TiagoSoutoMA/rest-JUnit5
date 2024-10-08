package br.com.rest.data.factory;

import br.com.rest.model.request.UsersRequest;

public class UsersDataFactory {

    public static UsersRequest createUserAdmin() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(BaseDataFactory.name());
        usersRequest.setEmail(BaseDataFactory.email());
        usersRequest.setPassword(BaseDataFactory.password());
        usersRequest.setAdministrador(String.valueOf(Boolean.TRUE));

        return usersRequest;
    }

    public static UsersRequest user() { return createUser(); }

    public static UsersRequest createUser() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(BaseDataFactory.name());
        usersRequest.setEmail(BaseDataFactory.email());
        usersRequest.setPassword(BaseDataFactory.password());
        usersRequest.setAdministrador(BaseDataFactory.isAdmin());

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyCredentials() {

        UsersRequest usersRequest = new UsersRequest();

        usersRequest.setNome(BaseDataFactory.empty());
        usersRequest.setEmail(BaseDataFactory.empty());
        usersRequest.setPassword(BaseDataFactory.empty());
        usersRequest.setAdministrador(BaseDataFactory.empty());

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyName() {

        UsersRequest usersRequest = user();

        usersRequest.setNome(BaseDataFactory.empty());

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyEmail() {

        UsersRequest usersRequest = user();

        usersRequest.setEmail(BaseDataFactory.empty());

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyPassword() {

        UsersRequest usersRequest = user();

        usersRequest.setPassword(BaseDataFactory.empty());

        return usersRequest;
    }

    public static UsersRequest createUserWithEmptyAdministrator() {

        UsersRequest usersRequest = user();

        usersRequest.setAdministrador(BaseDataFactory.empty());

        return usersRequest;
    }

    public static UsersRequest createUserWithInvalidEmail() {

        UsersRequest usersRequest = user();

        usersRequest.setEmail(BaseDataFactory.invalidEmail());

        return usersRequest;
    }

    public static UsersRequest createUserWithInvalidAdministrator() {

        UsersRequest usersRequest = user();

        usersRequest.setAdministrador(BaseDataFactory.name());

        return usersRequest;
    }

    public static UsersRequest createUserWithDuplicatedEmail() {

        UsersRequest usersRequest = user();

        usersRequest.setEmail(BaseDataFactory.emailProp());

        return usersRequest;
    }
}
