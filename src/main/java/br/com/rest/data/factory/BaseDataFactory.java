package br.com.rest.data.factory;

import br.com.rest.utils.Manipulation;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class BaseDataFactory {

    private static final Faker faker = new Faker(Locale.US);
    private static final Properties prop = Manipulation.getProp();

    public static String name() { return faker.name().fullName(); }
    public static String email() {
        return faker.internet().emailAddress();
    }
    public static String invalidEmail() {
        return faker.internet().emailAddress().replace("@", "");
    }
    public static String password() {
        return faker.internet().password();
    }
    public static String isAdmin() { return String.valueOf(faker.bool().bool()); }
    public static String nameProduct() { return faker.commerce().productName(); }
    public static int price() { return faker.number().numberBetween(1, 1000); }
    public static int invalidPrice() { return faker.number().numberBetween(-1000, 1); }
    public static String description() { return faker.commerce().material(); }
    public static int randomQuantity() { return faker.number().numberBetween(1, 100); }
    public static int invalidQuantity() { return faker.number().numberBetween(-100, 0); }
    public static int validQuantity(Integer quantity) { return faker.number().numberBetween(1, quantity); }
    public static String empty() {
        return StringUtils.EMPTY;
    }
    public static String emailProp() { return prop.getProperty("email"); }
    public static String passwordProp() { return prop.getProperty("password"); }
    public static String idAdminProp() { return prop.getProperty("id"); }
    public static String nameProductProp() { return prop.getProperty("nameProduct"); }
    public static String idCarrinhoProp() { return prop.getProperty("idCarrinho"); }
    public static String idProductCartProp() { return prop.getProperty("idProductCart"); }
}
