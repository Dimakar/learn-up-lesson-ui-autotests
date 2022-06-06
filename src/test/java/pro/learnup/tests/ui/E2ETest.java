package pro.learnup.tests.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.learnup.api.dto.PhoneDto;
import pro.learnup.extensions.UiTest;
import pro.learnup.pages.PhonesPage;
import pro.learnup.testdata.ApiTestDataHelper;
import pro.learnup.testdata.DbTestDataHelper;
import pro.learnup.testdata.User;

import static com.codeborne.selenide.Selenide.open;

@DisplayName("Покупка смартфона")
@Feature("Покупка смартфона")
@UiTest
public class E2ETest {
    User user;
    PhoneDto phoneDto;

    @BeforeEach
    void setUp() {
        user = ApiTestDataHelper.createTestUser();
        phoneDto = DbTestDataHelper.getAllPhones().get(0);
    }

    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Покупка смартфона")
    @Test
    public void buyPhoneTest() {
        open("/", PhonesPage.class)
                .getHeaderBlock()
                .login(user)
                .selectPhone(phoneDto.getInfo().getName())
                .checkPhoneName(phoneDto.getInfo().getName())
                .clickAddToCart()
                .getHeaderBlock()
                .goToCart()
                .checkCartContainExactly(phoneDto.getInfo().getName())
                .clickCheckOut()
                .clickConfirm()
                .checkThatCheckOutIsSuccessful();
    }

    @AfterEach
    void tearDown() {
        DbTestDataHelper.deleteUser(user.getId());
    }
}
