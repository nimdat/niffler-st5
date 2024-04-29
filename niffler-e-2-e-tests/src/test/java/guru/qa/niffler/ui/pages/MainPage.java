package guru.qa.niffler.ui.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.CollectionCondition.size;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class MainPage {
    private final ElementsCollection spendingRows = $(".spendings-table tbody").$$("tr");
    private final SelenideElement deleteSpendingBtn = $(".spendings__bulk-actions button");

    public SelenideElement findSpendingRowByCategoryName(String categoryName) {
        return spendingRows.find(text(categoryName));
    }

    public MainPage chooseSpending(SelenideElement spendingRow) {
        spendingRow.$$("td")
                .first()
                .scrollTo()
                .click();
        return this;
    }

    public MainPage deleteSpending() {
        deleteSpendingBtn.click();
        return this;
    }

    public void checkCountOfSpendings(int expectedSize) {
        spendingRows.shouldHave(size(expectedSize));
    }
}