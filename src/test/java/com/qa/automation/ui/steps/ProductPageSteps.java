package com.qa.automation.ui.steps;

import com.qa.automation.ui.pageobjects.ProductsPage;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;

public class ProductPageSteps {

    private final ProductsPage productsPage = new ProductsPage();

    public ProductPageSteps verifyProductsTitleDisplayed() {
        productsPage.productsTitle()
                .shouldBe(visible)
                .shouldHave(exactText("Products"));
        return this;
    }

    public ProductPageSteps verifyProductsDisplayed() {
        productsPage.products().shouldHave(sizeGreaterThan(0));
        return this;
    }

    public ProductPageSteps sortByPriceLowToHigh(String sortBy) {
        productsPage.sortProductsBy(sortBy);
        return this;
    }

    public ProductPageSteps verifyPricesSortedAscending() {
        List<Double> prices = productsPage.itemPrices().texts().stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();
        assertThat(prices)
                .as("Prices should be sorted low to high")
                .isSorted();
        return this;
    }
    
    public CartPageSteps addToCartandOpenCartPage(String productName) {
        productsPage.addToCart(productName);
        productsPage.openCartPage();
        return new CartPageSteps();   
    }       
}
