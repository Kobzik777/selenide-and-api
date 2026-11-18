package com.qa.automation.ui.steps;

import com.qa.automation.ui.pageobjects.CartPage;

import static com.codeborne.selenide.Condition.visible;

public class CartPageSteps {
    private final CartPage cartPage = new CartPage();

    public CartPageSteps verifyCartItems(String productName) {
        cartPage.cartItem(productName).shouldBe(visible);
        return this;
    }
}