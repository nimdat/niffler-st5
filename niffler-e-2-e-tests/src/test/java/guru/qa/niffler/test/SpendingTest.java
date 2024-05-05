package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.extension.CategoryExtension;
import guru.qa.niffler.jupiter.extension.SpendExtension;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.ui.pages.AuthPage;
import guru.qa.niffler.ui.pages.LoginPage;
import guru.qa.niffler.ui.pages.MainPage;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@ExtendWith({
        CategoryExtension.class,
        SpendExtension.class
})
public class SpendingTest {
    private final AuthPage authPage = new AuthPage();
    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();

    static {
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void doLogin() {
        Selenide.open("http://127.0.0.1:3000/main");
        authPage.clickLoginBtn();
        loginPage.setUsername("dima");
        loginPage.setPassword("12345");
        loginPage.clickSignInBtn();
    }

    @Test
    void anotherTest() {
        Selenide.open("http://127.0.0.1:3000/");
        $("a[href*='redirect']").should(visible);
    }

    @AfterEach
    void doScreenshot() {
        Allure.addAttachment(
                "Screen on test end",
                new ByteArrayInputStream(
                        Objects.requireNonNull(
                                Selenide.screenshot(OutputType.BYTES)
                        )
                )
        );
    }

    @GenerateCategory(
            category = "Обучение",
            username = "dima"
    )
    @GenerateSpend(
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        mainPage.selectSpendingByCategoryName(spendJson.category());
        mainPage.deleteSpending();
        mainPage.checkCountOfSpendings(0);
    }
}