package com.qa.automation.ui;

import org.testng.annotations.Test;


public class LoginTest extends BaseTest {

    @Test(groups = "ui")
    public void userCanLogInAndSeeProducts() {
        productPageSteps.verifyProductsTitleDisplayed()
        .verifyProductsDisplayed();
    }
}
