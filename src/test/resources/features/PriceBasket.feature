@PriceBasket
Feature: Price a Basket of Items
  As an application in the store
  I want to apply discounts
  So that I can price a basket

  Background:
    Given I have built the PriceBasket application

  Scenario: Multiple discounted items in Basket
    When I run PriceBasket Apples Milk Bread Soup Soup Soup Soup Soup Bread
    Then the Priced Basket shows Subtotal: £7.15
    And the Priced Basket shows Apples 10% off: -£0.10
    And the Priced Basket shows Bread 50% off: -£0.40
    And the Priced Basket shows Bread 50% off: -£0.40
    And the Priced Basket shows Total: £6.25

  Scenario: No discounted items in Basket
    When I run PriceBasket Milk Bread
    Then the Priced Basket shows Subtotal: £2.10
    And the Priced Basket shows (No offers available)
    And the Priced Basket shows Total: £2.10

  Scenario: Unknown items in Basket
    When I run PriceBasket Spanish-Inquisition
    Then the output shows Unexpected item "Spanish-Inquisition" found in basket!

  Scenario: Basket is empty
    When I run PriceBasket
    Then the output shows Usage: PriceBasket Apples Bread Soup Milk