package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.JdbcTest;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.ui.pages.AuthPage;
import guru.qa.niffler.ui.pages.LoginPage;
import guru.qa.niffler.ui.pages.MainPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@JdbcTest
public class SpendingJdbcTest {
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

    @GenerateCategory(
            category = "Обучение1",
            username = "dima"
    )
    @GenerateSpend(
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    @DisplayName("Удаление траты после изменений в db")
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson, CategoryJson categoryJson) {
        final SpendRepository spendRepository = SpendRepository.getInstance();
        CategoryEntity categoryEntity = CategoryEntity.fromJson(categoryJson);
        SpendEntity spendEntity = SpendEntity.fromJson(spendJson, categoryEntity);
        final String newNameCategory = "edit" + categoryJson.category();
        final String newNameDescription = "edit" + spendJson.description();

        categoryEntity.setCategory(newNameCategory);
        spendEntity.setDescription(newNameDescription);

        spendRepository.editCategory(categoryEntity);
        spendRepository.editSpand(spendEntity);

        Selenide.refresh();
        mainPage.selectSpendingByCategoryName(newNameCategory);
        mainPage.checkSpendingByDescriptionName(newNameDescription);
        mainPage.deleteSpending();
        mainPage.checkCountOfSpendings(0);
    }

    @GenerateCategory(
            category = "Обучение2",
            username = "dima"
    )
    @GenerateSpend(
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    @DisplayName("Трата удалена после изменений в db")
    void spendingShouldBeDeleted(SpendJson spendJson) {
        final SpendRepository spendRepository = SpendRepository.getInstance();
        spendRepository.removeSpend(SpendEntity.fromJson(spendJson));

        Selenide.refresh();
        mainPage.checkCountOfSpendings(0);
    }
}