package com.mclarnonworld.pricebasket.model.products;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration @Data
@ConfigurationProperties("catalogue")
public class ProductCatalogue {
    private List<Product> products = new ArrayList<>();
    private Map<String, Product> productMap;
}
