package br.com.rest.data.factory;

import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class DataFaker {

    static Faker faker = new Faker(Locale.US);

    public static String email() {
        return faker.internet().emailAddress();
    }

    public static String invalidEmail() {
        return faker.internet().emailAddress().replace("@", "");
    }

    public static String empty() {
        return StringUtils.EMPTY;
    }

    public static String password() {
        return faker.internet().password();
    }

    public static String name() { return faker.name().fullName(); }

    public static String isAdmin() { return String.valueOf(faker.bool().bool()); }

    public static String nameProduto() { return faker.commerce().productName(); }
    public static int price() { return faker.number().numberBetween(1, 1000); }
    public static String description() { return faker.commerce().material(); }
    public static int quantity() { return faker.number().numberBetween(1, 100); }
    public static int quantityValid(Integer quantity) { return faker.number().numberBetween(1, quantity); }
}
