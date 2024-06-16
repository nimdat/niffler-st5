package guru.qa.niffler.ui.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.CollectionCondition.size;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class MainPage {
    private final ElementsCollection spendingRows = $(".spendings-table tbody").$$("tr");
    private final SelenideElement deleteSpendingBtn = $(".spendings__bulk-actions button");
    private final SelenideElement peoplePageBtn = $("[data-tooltip-id='people']");
    private final SelenideElement friendsPageBtn = $("[data-tooltip-id='friends']");

    @Step("Выбрать расход по имени:{categoryName}")
    public MainPage selectSpendingByCategoryName(String categoryName) {
        spendingRows.find(text(categoryName))
                .$("td")
                .scrollTo()
                .click();
        return this;
    }

    @Step("Проверка текста описания {descriptionName}")
    public MainPage checkSpendingByDescriptionName(String descriptionName) {
        spendingRows.find(text(descriptionName)).shouldHave(text(descriptionName));
        return this;
    }

    @Step("Удалить расходы")
    public MainPage deleteSpending() {
        deleteSpendingBtn.click();
        return this;
    }

    @Step("Проверка количества расходов: {expectedSize}")
    public void checkCountOfSpendings(int expectedSize) {
        spendingRows.shouldHave(size(expectedSize));
    }

    @Step("Открыть страницу Friends")
    public void clickFriendsBtn() {
        friendsPageBtn.click();
    }

    @Step("Открыть страницу People")
    public void clickPeopleBtn() {
        peoplePageBtn.click();
    }
}