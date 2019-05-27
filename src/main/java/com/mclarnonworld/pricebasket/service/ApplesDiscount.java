package com.mclarnonworld.pricebasket.service;

import com.mclarnonworld.pricebasket.model.Basket;
import com.mclarnonworld.pricebasket.model.Item;
import com.mclarnonworld.pricebasket.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ApplesDiscount implements Discount {

	public void applyDiscount(Basket basket) {

		List<Item> itemsAfterDiscount = basket.getItems()
			.stream()
			.map(i -> {
				if (i.getProduct() == Product.APPLES) {
					i.setDiscount(Optional.of(new com.mclarnonworld.pricebasket.model.Discount(10)));
				}
				return i;
			})
			.collect(Collectors.toList());
		
		basket.setItems(itemsAfterDiscount);
	}
}
