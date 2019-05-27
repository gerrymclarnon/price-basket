package com.mclarnonworld.pricebasket.model;

import java.util.List;

public class PricedBasket {
    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getDiscountedTotal() {
        return discountedTotal;
    }

    public void setDiscountedTotal(int discountedTotal) {
        this.discountedTotal = discountedTotal;
    }

    public List<Item> getDiscountedItems() {
        return discountedItems;
    }

    public void setDiscountedItems(List<Item> discountedItems) {
        this.discountedItems = discountedItems;
    }

    private int subTotal;
    private int discountedTotal;
    private List<Item> discountedItems;
}
