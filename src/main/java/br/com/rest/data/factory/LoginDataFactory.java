package br.com.rest.data.factory;

import br.com.rest.model.request.LoginRequest;
import br.com.rest.utils.Manipulation;

import java.util.Properties;

public class LoginDataFactory {

    private static Properties prop = Manipulation.getProp();

    public static LoginRequest loginAdmin() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(prop.getProperty("email"));
        loginRequest.setPassword(prop.getProperty("password"));

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

        loginRequest.setEmail(DataFaker.email());
        loginRequest.setPassword(DataFaker.password());

        return loginRequest;
    }

    public static LoginRequest loginInvalidEmail() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(DataFaker.invalidEmail());
        loginRequest.setPassword(DataFaker.password());

        return loginRequest;
    }

    public static LoginRequest loginEmptyEmail() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(DataFaker.empty());
        loginRequest.setPassword(DataFaker.password());

        return loginRequest;
    }

    public static LoginRequest loginEmptyPassword() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(DataFaker.email());
        loginRequest.setPassword(DataFaker.empty());

        return loginRequest;
    }

    public static LoginRequest loginEmptyCredentials() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(DataFaker.empty());
        loginRequest.setPassword(DataFaker.empty());

        return loginRequest;
    }
}
