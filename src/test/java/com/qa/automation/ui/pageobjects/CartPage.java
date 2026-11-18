package com.qa.automation.ui.pageobjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class CartPage {

    private final String cartItems = "//*[@class='cart_item_label']//div[text()='%s']";

    public SelenideElement cartItem(String productName) {
        return $(By.xpath(String.format(cartItems, productName)));
    }

}