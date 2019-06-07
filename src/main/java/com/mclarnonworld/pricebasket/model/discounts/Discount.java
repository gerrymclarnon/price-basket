package com.mclarnonworld.pricebasket.model.discounts;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Discount {
    private Integer buy;
    private String of;
    private BigDecimal andGet;
    private String percentOff;
}
