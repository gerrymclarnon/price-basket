package com.mclarnonworld.pricebasket.ui;

import com.mclarnonworld.pricebasket.model.Item;
import com.mclarnonworld.pricebasket.model.PricedBasket;

import java.util.List;

public class PricedBasketOutput {

    public static void display(PricedBasket pricedBasket) {
        int subTotal = pricedBasket.getSubTotal();
        int discountedTotal = pricedBasket.getDiscountedTotal();
        List<Item> discountedItems = pricedBasket.getDiscountedItems();

        System.out.println(String.format("Subtotal: £%d.%d", subTotal / 100, subTotal % 100));

        if (discountedItems.size() == 0) {
            System.out.println("(No offers available)");
        } else {
            for (Item discountedItem : discountedItems) {
                System.out.println(String.format("%s %d%% off: -%dp", discountedItem.getProduct().getName(), discountedItem.getDiscountPercentage(), discountedItem.getDiscountValue()));
            }
        }

        System.out.println(String.format("Total: £%d.%d", discountedTotal / 100, discountedTotal % 100));
    }
}
