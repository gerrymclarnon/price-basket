package com.mclarnonworld.pricebasket.ui;

import com.mclarnonworld.pricebasket.model.baskets.Basket;
import com.mclarnonworld.pricebasket.model.baskets.PricedBasket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Component
public class PricedBasketDisplay {

    protected static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.UK);
    protected static final NumberFormat PERCENTAGE_FORMATTER = NumberFormat.getPercentInstance(Locale.UK);

    protected static final String NO_OFFERS_AVAILABLE = "(No offers available)";
    protected static final String SUBTOTAL = "Subtotal: %s";
    protected static final String TOTAL = "Total: %s";
    protected static final String DISCOUNT_OFF = "%s %s off: -%s";

    public void show(PricedBasket pricedBasket) {
        showSubTotal(pricedBasket.getSubTotal());
        showDiscountedItems(pricedBasket.getDiscountedItems());
        showDiscountedTotal(pricedBasket.getDiscountedTotal());
    }

    protected void showSubTotal(final BigDecimal subTotal) {
        System.out.println(String.format(SUBTOTAL, CURRENCY_FORMATTER.format(subTotal.doubleValue())));
    }

    protected void showDiscountedItems(final List<Basket.Item> discountedItems) {
        if (discountedItems.size() == 0) {
            System.out.println(NO_OFFERS_AVAILABLE);
        } else {
            for (Basket.Item discountedItem : discountedItems) {
                System.out.println(String.format(DISCOUNT_OFF,
                        discountedItem.getProduct().getName(),
                        PERCENTAGE_FORMATTER.format(discountedItem.getDiscountPercentage().doubleValue()),
                        CURRENCY_FORMATTER.format(discountedItem.getDiscountValue().doubleValue())));
            }
        }
    }

    protected void showDiscountedTotal(final BigDecimal discountedTotal) {
        System.out.println(String.format(TOTAL, CURRENCY_FORMATTER.format(discountedTotal.doubleValue())));
    }
}
