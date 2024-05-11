package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    void beforeLogin() {
        Selenide.open("http://127.0.0.1:3000/");
    }
}