package com.mclarnonworld.pricebasket.service;

import com.mclarnonworld.pricebasket.model.baskets.Basket;
import com.mclarnonworld.pricebasket.model.discounts.Discount;
import com.mclarnonworld.pricebasket.model.discounts.DiscountStore;
import com.mclarnonworld.pricebasket.model.baskets.PricedBasket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PriceBasketService {

    @Autowired
    private DiscountStore discountStore;

    public PricedBasket getPricedBasket(final Basket basket) {

        log.info("Get Priced Basket");

        applyDiscounts(basket);

        PricedBasket pricedBasket = new PricedBasket();

        pricedBasket.setSubTotal(this.calculateSubTotal(basket));
        pricedBasket.setDiscountedTotal(this.calculateDiscountedTotal(basket));
        pricedBasket.setDiscountedItems(this.getDiscountedProducts(basket));

        return pricedBasket;
    }

    protected void applyDiscounts(final Basket basket) {
        log.info("Applying discounts to basket");

        discountStore.getDiscounts().forEach(discount -> {
            long numberOfQualifyingItems = basket.getItems().stream()
                    .filter(item -> item.getProduct().getName().equals(discount.getOf())).count();
            long numberOfDeals = numberOfQualifyingItems / discount.getBuy();

            log.debug(String.format("Applying %d %s ", numberOfDeals, discount));

            basket.getItems().stream()
                    .filter(item -> item.getProduct().getName().equals(discount.getPercentOff()))
                    .limit(numberOfDeals)
                    .forEach(item -> {
                        item.setDiscount(Optional.of(discount));
                        item.setDiscountPercentage(getDiscountPercentage(item));
                        item.setDiscountValue(getDiscountValue(item));
                        item.setDiscountedPrice(getDiscountedPrice(item));
                    });
        });
    }

    public static BigDecimal getDiscountPercentage(Basket.Item item) {
        Optional<Discount> discount = item.getDiscount();
        return discount.isPresent() ? discount.get().getAndGet().divide(new BigDecimal(100), 2, RoundingMode.UNNECESSARY) : BigDecimal.ZERO;
    }

    public static BigDecimal getDiscountValue(Basket.Item item) {
        Optional<Discount> discount = item.getDiscount();
        return discount.isPresent() ? item.getProduct().getPrice().multiply(discount.get().getAndGet().divide(new BigDecimal(100), 2, RoundingMode.UNNECESSARY)) : BigDecimal.ZERO;
    }

    public static BigDecimal getDiscountedPrice(Basket.Item item) {
        Optional<Discount> discount = item.getDiscount();
        return discount.isPresent() ? item.getProduct().getPrice().subtract(getDiscountValue(item)) : item.getProduct().getPrice();
    }

    public BigDecimal calculateSubTotal(final Basket basket) {
        log.info("Calculating sub-total");

        return basket.getItems().stream()
                .map(i -> i.getProduct().getPrice())
                .reduce(BigDecimal::add)
                .get();
    }

    public BigDecimal calculateDiscountedTotal(final Basket basket) {
        log.info("Calculating discounted total");

        return basket.getItems().stream()
                .map(i -> i.getDiscountedPrice())
                .reduce(BigDecimal::add)
                .get();
    }

    public List<Basket.Item> getDiscountedProducts(final Basket basket) {
        log.info("Getting discounted products");

        return basket.getItems().stream()
                .filter(i -> i.getDiscount().isPresent())
                .collect(Collectors.toList());
    }
}
