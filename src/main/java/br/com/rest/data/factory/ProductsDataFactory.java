package br.com.rest.data.factory;

import br.com.rest.model.request.ProductsRequest;

public class ProductsDataFactory {

    public static ProductsRequest product() { return createProduct(); }

    public static ProductsRequest createProduct() {

        ProductsRequest product = new ProductsRequest();

        product.setNome(BaseDataFactory.nameProduct());
        product.setPreco(BaseDataFactory.price());
        product.setDescricao(BaseDataFactory.description());
        product.setQuantidade(BaseDataFactory.randomQuantity());

        return product;
    }

    public static ProductsRequest createProductWithInvalidData() {

        ProductsRequest product = new ProductsRequest();

        product.setNome(BaseDataFactory.empty());
        product.setPreco(BaseDataFactory.invalidPrice());
        product.setDescricao(BaseDataFactory.empty());
        product.setQuantidade(BaseDataFactory.invalidQuantity());

        return product;
    }

    public static ProductsRequest createProductWithEmptyName() {

        ProductsRequest product = product();

        product.setNome(BaseDataFactory.empty());

        return product;
    }

    public static ProductsRequest createProductWithInvalidPrice() {

        ProductsRequest product = product();

        product.setPreco(BaseDataFactory.invalidPrice());

        return product;
    }

    public static ProductsRequest createProductWithEmptyDescription() {

        ProductsRequest product = product();

        product.setDescricao(BaseDataFactory.empty());

        return product;
    }

    public static ProductsRequest createProductWithInvalidQuantity() {

        ProductsRequest product = product();

        product.setQuantidade(BaseDataFactory.invalidQuantity());

        return product;
    }

    public static ProductsRequest createProductWithDuplicatedName() {

        ProductsRequest product = product();

        product.setNome(BaseDataFactory.nameProductProp());

        return product;
    }
}
