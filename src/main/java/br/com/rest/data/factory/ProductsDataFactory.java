package br.com.rest.data.factory;

import br.com.rest.client.ProdutctsClient;
import br.com.rest.model.request.ProductsRequest;
import br.com.rest.utils.Manipulation;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class ProductsDataFactory {

    private static ProdutctsClient productsClient = new ProdutctsClient();
    private static Properties prop = Manipulation.getProp();

    public static ProductsRequest createProduct() {

        ProductsRequest product = new ProductsRequest();

        product.setNome(DataFaker.nameProduto());
        product.setPreco(DataFaker.price());
        product.setDescricao(DataFaker.description());
        product.setQuantidade(DataFaker.quantity());

        return product;
    }

    public static ProductsRequest createProductWithInvalidData() {

        ProductsRequest product = new ProductsRequest();

        product.setNome(StringUtils.EMPTY);
        product.setPreco(0);
        product.setDescricao(StringUtils.EMPTY);
        product.setQuantidade(-1);

        return product;
    }

    public static ProductsRequest createProductWithEmptyName() {

        ProductsRequest product = new ProductsRequest();

        product.setNome(StringUtils.EMPTY);
        product.setPreco(DataFaker.price());
        product.setDescricao(DataFaker.description());
        product.setQuantidade(DataFaker.quantity());

        return product;
    }

    public static ProductsRequest createProductWithInvalidPrice() {

        ProductsRequest product = new ProductsRequest();

        product.setNome(DataFaker.nameProduto());
        product.setPreco(0);
        product.setDescricao(DataFaker.description());
        product.setQuantidade(DataFaker.quantity());

        return product;
    }

    public static ProductsRequest createProductWithEmptyDescription() {

        ProductsRequest product = new ProductsRequest();

        product.setNome(DataFaker.nameProduto());
        product.setPreco(DataFaker.price());
        product.setDescricao(StringUtils.EMPTY);
        product.setQuantidade(DataFaker.quantity());

        return product;
    }

    public static ProductsRequest createProductWithInvalidQuantity() {

        ProductsRequest product = new ProductsRequest();

        product.setNome(DataFaker.nameProduto());
        product.setPreco(DataFaker.price());
        product.setDescricao(DataFaker.description());
        product.setQuantidade(-1);

        return product;
    }

    public static ProductsRequest createProductWithDuplicatedName() {

        ProductsRequest product = new ProductsRequest();

        product.setNome(prop.getProperty("nameProduct"));
        product.setPreco(DataFaker.price());
        product.setDescricao(DataFaker.description());
        product.setQuantidade(DataFaker.quantity());

        return product;
    }

    public static String empty() { return DataFaker.empty(); }
    public static String name() { return DataFaker.name(); }

    public static String idProductCart() { return prop.getProperty("idProductCart"); }
}
