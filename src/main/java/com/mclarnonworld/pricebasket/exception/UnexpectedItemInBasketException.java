package com.mclarnonworld.pricebasket.exception;

public class UnexpectedItemInBasketException extends RuntimeException {

    public UnexpectedItemInBasketException(String message) {
        super(message);
    }
}
