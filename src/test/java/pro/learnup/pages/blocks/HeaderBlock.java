package pro.learnup.pages.blocks;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pro.learnup.pages.CartPage;
import pro.learnup.pages.PhonesPage;
import pro.learnup.pages.elements.Button;
import pro.learnup.testdata.User;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class HeaderBlock {

    private SelenideElement logout = $(byText("LOGOUT"));

    @Step("Авторизоваться пользователем {login} {password}")
    public PhonesPage login(String login, String password) {
        clickLoginButton()
                .inputLogin(login)
                .inputPassword(password)
                .clickSubmitButton()
                .checkLogoutButtonIsVisible();
        return page(PhonesPage.class);
    }

    @Step("Авторизоваться пользователем {user.login} {user.password}")
    public PhonesPage login(User user) {
        return login(user.getLogin(), user.getPassword());
    }

    @Step("Перейти в корзину")
    public CartPage goToCart() {
        $(By.xpath("//a[.='CART']")).click();
        return page(CartPage.class);
    }

    @Step("Ввести пароль")
    public HeaderBlock inputPassword(String password) {
        $x("//input[contains(@id, 'Password')]").sendKeys(password);
        return this;
    }

    @Step("Ввести логин")
    public HeaderBlock inputLogin(String login) {
        $x("//input[contains(@id, 'Username')]").sendKeys(login);
        return this;
    }

    public HeaderBlock clickLoginButton() {
        new Button("LOGIN").click();
        return this;
    }

    public HeaderBlock clickSubmitButton() {
        new Button("Submit").click();
        return this;
    }

    @Step("Проверить, что кнопка LOGOUT отображается")
    public HeaderBlock checkLogoutButtonIsVisible() {
        logout.shouldBe(Condition.visible);
        return this;
    }

}
