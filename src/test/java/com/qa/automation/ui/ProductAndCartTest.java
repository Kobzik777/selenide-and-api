package com.qa.automation.ui;

import org.testng.annotations.Test;

public class ProductAndCartTest extends BaseTest {
    @Test(groups = "ui")
    public void userCanAddProductToCart() {
       productPageSteps
        .sortByPriceLowToHigh("Price (low to high)")
        .verifyPricesSortedAscending()
        .addToCartandOpenCartPage("Sauce Labs Backpack")
        .verifyCartItems("Sauce Labs Backpack");
    }
}