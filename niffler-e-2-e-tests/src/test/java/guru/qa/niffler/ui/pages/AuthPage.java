package guru.qa.niffler.ui.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.byText;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class AuthPage {

    private final SelenideElement mainBtns = $(".main__links");
    private final SelenideElement loginBtn = mainBtns.$(byText("Login"));
    private final SelenideElement registerBtn = mainBtns.$(byText("Register"));

    @Step("Нажать на кнопку Login")
    public void clickLoginBtn() {
        loginBtn.click();
    }
}