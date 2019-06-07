package com.mclarnonworld.pricebasket.model.baskets;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PricedBasket {
    private BigDecimal subTotal;
    private BigDecimal discountedTotal;
    private List<Basket.Item> discountedItems;
}
