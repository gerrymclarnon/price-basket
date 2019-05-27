package com.mclarnonworld.pricebasket;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Multiply {
    public static void main(String[] args) {
        BigDecimal d = new BigDecimal("1115.32");
        BigDecimal taxRate = new BigDecimal("0.0049");
        BigDecimal d2 = d.multiply(taxRate);
        System.out.println("Unformatted: " + d2.toString());
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.UK);
        double money = d2.doubleValue();
        String s = n.format(money);
        System.out.println("Formatted:  " + s);
    }
}
