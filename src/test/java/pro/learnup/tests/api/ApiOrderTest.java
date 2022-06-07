package pro.learnup.tests.api;

import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pro.learnup.api.dto.Order;
import pro.learnup.api.dto.OrderRequestDto;
import pro.learnup.api.dto.PhoneDto;
import pro.learnup.api.endpoints.ApiCatalogEndpoint;
import pro.learnup.api.endpoints.ApiOrderEndpoint;
import pro.learnup.api.endpoints.ApiUserEndpoint;
import pro.learnup.extensions.ApiTest;
import pro.learnup.testdata.ApiTestDataHelper;
import pro.learnup.testdata.DbTestDataHelper;
import pro.learnup.testdata.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("/api/order: Оформление заказа")
@Feature("Покупка смартфона")
@ApiTest
public class ApiOrderTest {
    static User user;
    List<Order> orders;

    @BeforeAll
    @Step("Подготовка ТД")
    static void beforeAll() {
        user = ApiTestDataHelper.createTestUser();
    }

    public static Stream<PhoneDto> phones() {
        return new ApiCatalogEndpoint().getAllPhones().stream();
    }

    @AfterAll
    @Step("Удаление ТД")
    static void tearDown() {
        DbTestDataHelper.deleteUser(user.getId());
    }

    @BeforeEach
    @Step("Подготовка ТД")
    void setUp() {
        orders = new ApiUserEndpoint().getUser(ApiOrderTest.user).getOrders();
    }

    @DisplayName("/api/order: 200: успешное оформление заказа")
    @ParameterizedTest(name = "/api/order: 200: успешное оформление заказа различных смартфонов")
    @AllureId("746")
    @MethodSource("phones")
    void apiOrderSuccessfulTest(PhoneDto phoneDto) {
//        parameter("Модель смартфона", phoneDto.getInfo().getName());
        Order expectedOrder = Order.builder()
                .dateCreated(LocalDateTime.now().withNano(0))
                .name(phoneDto.getInfo().getName())
                .price(phoneDto.getInfo().getPrice())
                .quantity(1)
                .build();

        new ApiOrderEndpoint().orderPhones(user, OrderRequestDto.builder()
                .order(expectedOrder)
                .build());

        orders.add(expectedOrder);
        step("Проверить через API, что ордер появился в списке у юзера", () ->
                assertThat(new ApiUserEndpoint().getUser(user).getOrders())
                        .as("У юзера добавился еще один заказ")
                        .containsExactlyInAnyOrderElementsOf(orders));
    }
}
