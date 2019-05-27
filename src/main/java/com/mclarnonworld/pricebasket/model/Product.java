package com.mclarnonworld.pricebasket.model;

import com.mclarnonworld.pricebasket.exception.UnexpectedItemInBasketException;

public enum Product {

    SOUP("Soup", 65),
    BREAD("Bread", 80),
    MILK("Milk", 130),
    APPLES("Apples", 100);

    public static Product getProductByName(String name) throws UnexpectedItemInBasketException {

        Product product;

        try {
            product = Product.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnexpectedItemInBasketException(String.format("Unexpected item \"%s\" found in basket!", name));
        }

        return product;
    }

    private String name;
    private int price;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
