package pro.learnup.tests.ui;

import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.learnup.api.dto.ItemDto;
import pro.learnup.api.dto.PhoneDto;
import pro.learnup.api.endpoints.ApiCartEndpoint;
import pro.learnup.extensions.UiTest;
import pro.learnup.pages.PhonesPage;
import pro.learnup.testdata.DbTestDataHelper;
import pro.learnup.testdata.User;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static pro.learnup.steps.UiSteps.openPage;
import static pro.learnup.testdata.ApiTestDataHelper.createTestUser;

@UiTest
@Feature("Покупка смартфона")
@DisplayName("Добавление смартфона в корзину")
public class AddToCartTest {
    static User user;
    static PhoneDto phoneDto;

    @BeforeAll
    @Step("Подготовка ТД")
    static void setUp() {
        user = createTestUser();
        phoneDto = DbTestDataHelper.getAllPhones().get(0);
    }

    @AfterAll
    @Step("Удаление ТД")
    static void tearDown() {
        DbTestDataHelper.deleteUser(user.getId());
    }

    @Test
    @DisplayName("Успешное добавление смартфона в корзину авторизованным пользователем")
    void addToCartTest() {
        openPage(user, PhonesPage.class)
                .selectPhone(phoneDto.getInfo().getName())
                .checkPhoneName(phoneDto.getInfo().getName())
                .clickAddToCart()
                .checkAlert("Item added to your cart.");

        step("Проверить через API, что смартфон добавлен в корзину", () -> {
            List<ItemDto> items = new ApiCartEndpoint().getCart(user).getItems();
            assertThat(items).hasSize(1);
            assertThat(items.get(0))
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(ItemDto.builder()
                            .product(phoneDto)
                            .quantity(1)
                            .build());
        });
    }

    @Test
    
    @DisplayName("Ошибка при добавлении товара в корзину неавторизованным пользователем")
    void addToCartByNotAuthTest() {
        openPage(PhonesPage.class)
                .selectPhone(phoneDto.getInfo().getName())
                .checkPhoneName(phoneDto.getInfo().getName())
                .clickAddToCart()
                .checkAlert("You must be logged in!");
    }
}
