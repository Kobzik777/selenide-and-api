package com.qa.automation.ui.steps;

import com.qa.automation.ui.pageobjects.LoginPage;

public class LoginPageSteps {

    private final LoginPage loginPage = new LoginPage();

    public LoginPageSteps openLoginPage() {
        loginPage.openPage();
        return new LoginPageSteps();
    }

    public LoginPageSteps login(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        return this;
    }
}
