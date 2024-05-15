package guru.qa.niffler.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;

public class PeoplePage {
    private final SelenideElement peopleTable = $(".abstract-table tbody");
    private final ElementsCollection peopleRows = peopleTable.$$("tr");
    private final String submitBtn = ".button-icon_type_submit";

    @Step("Проверка наличие кнопки Submit invitation")
    public PeoplePage checkPeopleRequestBtn(String name) {
        peopleRows.find(text(name)).$(submitBtn).shouldHave(visible);
        return this;
    }

    @Step("Проверка наличия имени: {name} и статуса: {status} в таблице People")
    public PeoplePage checkNameAndStatusByPeopleTable(String name, String status) {
        peopleRows.find(text(name)).shouldHave(text(status));
        return this;
    }
}