package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.ui.pages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.*;

@ExtendWith({BrowserExtension.class,
        UserQueueExtension.class})
public class UserQueueBeforeEachTest extends BaseTest {
    private final AuthPage authPage = new AuthPage();
    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();
    private final FriendsPage friendsPage = new FriendsPage();

    @BeforeEach
    void doLogin(@User(WITH_FRIENDS) UserJson user) {
        authPage.clickLoginBtn();
        loginPage.setUsername(user.username())
                .setPassword(user.testData().password());
        loginPage.clickSignInBtn();
    }

    @Test
    @DisplayName("В списке друзей, присутствует друг")
    void checkFriendshipInFriendsPage() {
        mainPage.clickFriendsBtn();
        friendsPage.checkNameAndStatusByFriendsTable("user3", "You are friend");
    }
}