package com.qa.automation.ui.pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductsPage {

    private final String productsTitle = "//*[@id='header_container']/div[2]/span";
    private final ElementsCollection products = $$(".inventory_item");
    private final String productSortContainer = "//*[@class='product_sort_container']";
    private final ElementsCollection itemPrices = $$(".inventory_item_price");
    private final String addToCartButton = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button";
    private final String cartButton = "//a[@class='shopping_cart_link']";
  
    public SelenideElement productsTitle() {
        return $(By.xpath(productsTitle));
    }

    public ElementsCollection products() {
        return products;
    }

    public void sortProductsBy(String sortBy) {
        $(By.xpath(productSortContainer)).selectOption(sortBy);
    }

    public ElementsCollection itemPrices() {
        return itemPrices;
    }

    public void addToCart(String productName) {
        $(By.xpath(String.format(addToCartButton, productName))).click();
    }
    
    public void openCartPage() {
        $(By.xpath(cartButton)).click();
    }
}
