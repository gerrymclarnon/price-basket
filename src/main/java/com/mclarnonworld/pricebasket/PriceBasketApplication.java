package com.mclarnonworld.pricebasket;

import com.mclarnonworld.pricebasket.exception.UnexpectedItemInBasketException;
import com.mclarnonworld.pricebasket.model.Basket;
import com.mclarnonworld.pricebasket.model.PricedBasket;
import com.mclarnonworld.pricebasket.service.PriceBasketService;
import com.mclarnonworld.pricebasket.ui.PricedBasketOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PriceBasketApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(PriceBasketApplication.class);

	@Autowired
	PriceBasketService priceBasketService;

	@Override
	public void run(String... args) {
		Basket basket = new Basket();
		try {
			basket.setItems(args);
		}
		catch (UnexpectedItemInBasketException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		if (basket.getItems().size() == 0) {
			System.out.println("Usage: PriceBasket Apples Bread Soup Milk");
			return;
		}

		PricedBasket pricedBasket = priceBasketService.getPricedBasket(basket);

		PricedBasketOutput.display(pricedBasket);
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PriceBasketApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}