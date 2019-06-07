package com.mclarnonworld.pricebasket.model.discounts;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration @Data
@ConfigurationProperties("store")
public class DiscountStore {
    private List<Discount> discounts;
}
