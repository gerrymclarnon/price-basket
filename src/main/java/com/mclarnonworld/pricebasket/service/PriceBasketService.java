package com.mclarnonworld.pricebasket.service;

import com.mclarnonworld.pricebasket.model.Basket;
import com.mclarnonworld.pricebasket.model.Item;
import com.mclarnonworld.pricebasket.model.PricedBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceBasketService {
	
	@Autowired
	public PriceBasketService() {}

	@Autowired
	@Qualifier("soupDiscount")
	Discount soupDiscount;

	@Autowired
	@Qualifier("applesDiscount")
	Discount applesDiscount;

	public PricedBasket getPricedBasket(final Basket basket) {
		this.applyDiscounts(basket);

		PricedBasket pricedBasket = new PricedBasket();

		pricedBasket.setSubTotal(this.calculateSubTotal(basket));
		pricedBasket.setDiscountedTotal(this.calculateDiscountedTotal(basket));
		pricedBasket.setDiscountedItems(this.getDiscountedProducts(basket));

		return pricedBasket;
	}

	public void applyDiscounts(final Basket basket) {
		applesDiscount.applyDiscount(basket);
		soupDiscount.applyDiscount(basket);
	}

	public int calculateSubTotal(final Basket basket) {
		return basket.getItems()
				.stream()
				.mapToInt(i -> i.getPrice())
				.sum();
	}
	
	public int calculateDiscountedTotal(final Basket basket) {
		return basket.getItems()
				.stream()
				.mapToInt(i -> i.getDiscountedPrice())
				.sum();
	}

	public List<Item> getDiscountedProducts(final Basket basket) {
		return basket.getItems()
				.stream()
				.filter(i -> i.getDiscount().isPresent())
				.collect(Collectors.toList());
	}
}
