package guru.qa.niffler.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;

public class FriendsPage {
    private final SelenideElement friendTable = $(".abstract-table tbody");
    private final ElementsCollection friendRows = friendTable.$$("tr");
    private final String submitBtn = ".button-icon_type_submit";

    @Step("Проверка наличие кнопки Submit invitation")
    public FriendsPage checkFriendRequestBtn(String name) {
        friendRows.find(text(name)).$(submitBtn).shouldHave(visible);
        return this;
    }

    @Step("Проверка наличия имени: {user} и статуса: {status} в таблице Friends")
    public FriendsPage checkNameAndStatusByFriendsTable(String name, String state) {
        friendRows.find(text(name)).shouldHave(text(state));
        return this;
    }
}