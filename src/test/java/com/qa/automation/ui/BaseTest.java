package com.qa.automation.ui;

import com.codeborne.selenide.Configuration;
import com.qa.automation.ui.steps.LoginPageSteps;
import com.qa.automation.ui.steps.ProductPageSteps;
import com.codeborne.selenide.Selenide;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class BaseTest {

    protected final LoginPageSteps loginPageSteps = new LoginPageSteps();
    protected final ProductPageSteps productPageSteps = new ProductPageSteps();

 
    protected final Dotenv env = Dotenv.configure().ignoreIfMissing().load();

    @BeforeSuite
    @Parameters("headless")
    public void configureBrowser(@Optional("true") String headless) {
        Configuration.headless = Boolean.parseBoolean(headless);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        Configuration.browserCapabilities = options;
    }

    @BeforeMethod
    public void login() {
        loginPageSteps.openLoginPage();
        loginPageSteps.login(env.get("UI_USERNAME"), env.get("UI_PASSWORD"));
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
