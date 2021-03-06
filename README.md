# Pricing a basket 

## Overview

A program and associated unit tests that can price a basket of goods taking into account some special offers. 

The goods that can be purchased, together with their normal prices are: 

* Soup – 65p per tin
* Bread – 80p per loaf
* Milk – £1.30 per bottle
* Apples – £1.00 per bag 

Current special offers are: 

* Apples have a 10% discount off their normal price this week
* Buy 2 tins of soup and andGet a loaf of bread for half price 

The program accepts a list of items in the basket and output the subtotal, the special offer discounts and the final price. 

Input is via the command line in the form PriceBasket item1 item2 item3 … 

For example: 

```
> PriceBasket Apples Milk Bread 
```

Output is to the console, for example: 

```
Subtotal: £3.10 
Apples 10% off: -10p 
Total: £3.00 
```

If no special offers are applicable the output will be: 

```
Subtotal: £1.30 
(No offers available) 
Total: £1.30 
```

## Implementation details

This has been set up as Spring Boot project using Java 8 features.  The application is built with Maven.

## Example

```
> mvn package
> java -jar target\price-basket-0.0.1-SNAPSHOT.jar Apples Bread Bread Soup Soup
Subtotal: 3.90
Apples 10% off: -10p
Bread 50% off: -40p
Total: 3.40
```

