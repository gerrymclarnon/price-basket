package com.mclarnonworld.pricebasket.model;

import com.mclarnonworld.pricebasket.service.SoupDiscount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Basket {

    List<Item> items = new ArrayList<>();

    List<SoupDiscount> discountRules = new ArrayList<>();

    public Basket() {
    }

    public Basket(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(String... items) {
        this.setItems(Arrays.asList(items)
                .stream()
                .map(p -> new Item(Product.getProductByName(p)))
                .collect(Collectors.toList()));
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item product) {
        items.add(product);
    }

    public List<SoupDiscount> getDiscountRules() {
        return discountRules;
    }

    public void setDiscountRules(List<SoupDiscount> discountRules) {
        this.discountRules = discountRules;
    }

    public void addDiscountRule(SoupDiscount discountRule) {
        discountRules.add(discountRule);
    }
}
