package pro.learnup.tests.ui;

import io.qameta.allure.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.learnup.extensions.UiTest;
import pro.learnup.pages.PhonesPage;
import pro.learnup.testdata.ApiTestDataHelper;
import pro.learnup.testdata.DbTestDataHelper;
import pro.learnup.testdata.User;

import static com.codeborne.selenide.Selenide.open;

@DisplayName("Авторизация пользователя")
@Feature("Авторизация")
@UiTest
public class AuthTest {

    User user;

    @BeforeEach
    void setUp() {
        user = ApiTestDataHelper.createTestUser();
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Успешная авторизация пользователя")
    @Test
    public void authSuccessfulTest() {
        open("/", PhonesPage.class)
                .getHeaderBlock()
                .login(user);
    }

    @AfterEach
    void tearDown() {
        DbTestDataHelper.deleteUser(user.getId());
    }
}
