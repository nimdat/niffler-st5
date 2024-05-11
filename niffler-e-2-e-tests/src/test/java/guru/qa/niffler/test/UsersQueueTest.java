package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.ui.pages.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.*;

@ExtendWith({BrowserExtension.class,
        UserQueueExtension.class})
public class UsersQueueTest extends BaseTest {
    private final AuthPage authPage = new AuthPage();
    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();
    private final PeoplePage peoplePage = new PeoplePage();
    private final FriendsPage friendsPage = new FriendsPage();

    @Test
    @DisplayName("В списке друзей, присутствует запрос на дружбу")
    void checkFriendshipRequestInFriendsPage(@User(INVITATION_SEND) UserJson userSend,
                                            @User(INVITATION_RECEIVED) UserJson userReceived) {
        authPage.clickLoginBtn();
        loginPage.setUsername(userReceived.username())
                .setPassword(userReceived.testData().password());
        loginPage.clickSignInBtn();
        mainPage.clickFriendsBtn();
        friendsPage.checkFriendRequestBtn(userSend.username());
    }

    @Test
    @DisplayName("В списке людей, присутствует запрос на дружбу")
    void checkFriendshipRequestInPeoplePage(@User(INVITATION_SEND) UserJson userSend,
                    @User(INVITATION_RECEIVED) UserJson userReceived) {
        authPage.clickLoginBtn();
        loginPage.setUsername(userReceived.username())
                .setPassword(userReceived.testData().password());
        loginPage.clickSignInBtn();
        mainPage.clickPeopleBtn();;
        peoplePage.checkPeopleRequestBtn(userSend.username());
    }

    @Test
    @DisplayName("В списке людей, присутствует друг")
    void checkFriendshipInPeoplePage(@User(WITH_FRIENDS) UserJson user) {
        authPage.clickLoginBtn();
        loginPage.setUsername(user.username())
                .setPassword(user.testData().password());
        loginPage.clickSignInBtn();
        mainPage.clickPeopleBtn();
        peoplePage.checkNameAndStatusByPeopleTable("user3", "You are friend");
    }
}