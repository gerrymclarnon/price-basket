package com.mclarnonworld.pricebasket.service;

import com.mclarnonworld.pricebasket.model.Basket;
import com.mclarnonworld.pricebasket.model.Item;
import com.mclarnonworld.pricebasket.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class SoupDiscount implements Discount {

	public void applyDiscount(Basket basket) {
		
		List<Item> soupItems = basket.getItems()
			.stream()
			.filter(i -> i.getProduct() == Product.SOUP)
			.collect(Collectors.toList());
		
		int numberOfSoupItems = soupItems.size();
		int numberOfMultiBuyDeals = numberOfSoupItems / 2;
		
		AtomicInteger numberOfDiscounts = new AtomicInteger(0);
		List<Item> itemsAfterDiscount = basket.getItems()
			.stream()
			.map(i -> {
				if (i.getProduct() == Product.BREAD && numberOfDiscounts.intValue() < numberOfMultiBuyDeals) {
					i.setDiscount(Optional.of(new com.mclarnonworld.pricebasket.model.Discount(50)));
					numberOfDiscounts.incrementAndGet();
				}
				return i;
	        })
			.collect(Collectors.toList());
		
		basket.setItems(itemsAfterDiscount);
	}
}
