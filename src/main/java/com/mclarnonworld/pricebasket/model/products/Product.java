package com.mclarnonworld.pricebasket.model.products;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private java.lang.String name;
    private BigDecimal price;
}
