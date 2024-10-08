package br.com.rest.data.factory;

import br.com.rest.model.request.LoginRequest;

public class LoginDataFactory {

    public static LoginRequest loginAdmin() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(BaseDataFactory.emailProp());
        loginRequest.setPassword(BaseDataFactory.passwordProp());

        return loginRequest;
    }

    public static LoginRequest login(String email, String password) {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        return loginRequest;
    }

    public static LoginRequest loginCredentialsNotRegistered() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(BaseDataFactory.email());
        loginRequest.setPassword(BaseDataFactory.password());

        return loginRequest;
    }

    public static LoginRequest loginInvalidEmail() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(BaseDataFactory.invalidEmail());
        loginRequest.setPassword(BaseDataFactory.password());

        return loginRequest;
    }

    public static LoginRequest loginEmptyEmail() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(BaseDataFactory.empty());
        loginRequest.setPassword(BaseDataFactory.password());

        return loginRequest;
    }

    public static LoginRequest loginEmptyPassword() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(BaseDataFactory.email());
        loginRequest.setPassword(BaseDataFactory.empty());

        return loginRequest;
    }

    public static LoginRequest loginEmptyCredentials() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(BaseDataFactory.empty());
        loginRequest.setPassword(BaseDataFactory.empty());

        return loginRequest;
    }
}
