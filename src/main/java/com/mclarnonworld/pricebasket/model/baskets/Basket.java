package com.mclarnonworld.pricebasket.model.baskets;

import com.mclarnonworld.pricebasket.PriceBasketApplication;
import com.mclarnonworld.pricebasket.exception.UnexpectedItemInBasketException;
import com.mclarnonworld.pricebasket.model.discounts.Discount;
import com.mclarnonworld.pricebasket.model.products.Product;
import com.mclarnonworld.pricebasket.model.products.ProductCatalogue;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
public class Basket {
    private List<Item> items = new ArrayList<>();

    public static Basket.BasketBuilder builder() {
        return new Basket.BasketBuilder();
    }

    @Slf4j
    public static class BasketBuilder {
        private String[] items;
        private ProductCatalogue productCatalogue;

        BasketBuilder() {
        }

        public Basket.BasketBuilder productCatalogue(final ProductCatalogue productCatalogue) {
            this.productCatalogue = productCatalogue;
            return this;
        }

        public Basket.BasketBuilder items(final String[] items) {
            this.items = items;
            return this;
        }

        public Basket build() {
            Map<String, Product> productMap =
                    productCatalogue.getProducts().stream()
                            .collect(Collectors.toMap(Product::getName, product -> product));

            Basket basket = new Basket();
            try {
                List<Basket.Item> basketItems =
                        Arrays.asList(this.items)
                                .stream()
                                .map(name -> getItem(productMap, name))
                                .collect(Collectors.toList());

                basket.setItems(basketItems);
            } catch (UnexpectedItemInBasketException e) {
                log.error(e.getMessage());
                System.out.println(e.getMessage());
                System.exit(0);
            }
            return basket;
        }

        protected Basket.Item getItem(Map<String, Product> productMap, String name) {
            Product product = productMap.get(name);

            if (product == null) {
                throw new UnexpectedItemInBasketException(String.format("Unexpected item \"%s\" found in basket!", name));
            }

            return Basket.Item
                    .builder()
                    .product(product)
                    .discount(Optional.empty())
                    .discountedPrice(product.getPrice())
                    .build();
        }

        public String toString() {
            return "Basket.BasketBuilder(items=" + this.items + ")";
        }
    }

    @Data
    @Builder
    public static class Item {
        private Product product;
        private Optional<Discount> discount;

        private BigDecimal discountPercentage;
        private BigDecimal discountValue;
        private BigDecimal discountedPrice;
    }
}
