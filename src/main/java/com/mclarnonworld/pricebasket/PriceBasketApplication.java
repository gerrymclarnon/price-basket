package com.mclarnonworld.pricebasket;

import com.mclarnonworld.pricebasket.model.baskets.Basket;
import com.mclarnonworld.pricebasket.model.baskets.PricedBasket;
import com.mclarnonworld.pricebasket.model.products.ProductCatalogue;
import com.mclarnonworld.pricebasket.service.PriceBasketService;
import com.mclarnonworld.pricebasket.ui.PricedBasketDisplay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PriceBasketApplication implements CommandLineRunner {

    @Autowired
    private ProductCatalogue productCatalogue;

    @Autowired
    private PriceBasketService priceBasketService;

    @Autowired
    private PricedBasketDisplay pricedBasketDisplay;

    @Override
    public void run(String... args) {
        Basket basket = Basket
                .builder()
                .items(args)
                .productCatalogue(productCatalogue)
                .build();

        if (basket.getItems().size() == 0) {
            log.error("No basket items supplied on command line!");
            System.out.println("Usage: PriceBasket Apples Bread Soup Milk");
            return;
        }

        PricedBasket pricedBasket = priceBasketService.getPricedBasket(basket);
        pricedBasketDisplay.show(pricedBasket);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PriceBasketApplication.class);
        app.run(args);
    }
}