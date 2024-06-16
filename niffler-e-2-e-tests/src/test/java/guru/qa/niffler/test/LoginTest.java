package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.UserRepositorySpringJdbc;
import guru.qa.niffler.jupiter.annotation.meta.TestUser;
import guru.qa.niffler.model.CurrencyValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@TestUser
public class LoginTest {

    UserRepository userRepository = new UserRepositorySpringJdbc();
    UserEntity userDataUser;

    @BeforeEach
    void createUserForTest() {
        UserAuthEntity user = new UserAuthEntity();
        user.setUsername("jdbc_user6");
        user.setPassword("12345");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        userRepository.createUserInAuth(user);


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("jdbc_user6");
        userEntity.setCurrency(CurrencyValues.RUB);
        userDataUser = userRepository.createUserInUserData(userEntity);
    }

    @Test
    void loginTest() {
        Selenide.open("http://127.0.0.1:3000/");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue("jdbc_user6");
        $("input[name='password']").setValue("12345");
        $("button[type='submit']").click();
        $(".header__avatar").should(visible);

        userRepository.findUserInUserdataById(userDataUser.getId());
    }
}