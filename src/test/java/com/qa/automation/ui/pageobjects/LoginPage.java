package com.qa.automation.ui.pageobjects;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final String usernameInput = "//input[@id='user-name']";
    private final String passwordInput = "//input[@id='password']";
    private final String loginButton = "//input[@id='login-button']";

    public void openPage() {
        open("/");
    }

    public void enterUsername(String username) {
        $(By.xpath(usernameInput)).setValue(username);
    }

    public void enterPassword(String password) {
        $(By.xpath(passwordInput)).setValue(password);
    }

    public void clickLoginButton() {
        $(By.xpath(loginButton)).click();
    }
}
