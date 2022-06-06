package pro.learnup.api.endpoints;

import io.qameta.allure.Step;
import pro.learnup.api.dto.OrderRequestDto;
import pro.learnup.testdata.User;

import static io.restassured.RestAssured.given;

@Endpoint("/api/order")
public class ApiOrderEndpoint extends BaseEndpoint {

    @Step("{this.endpoint}: POST: 200: Успешное оформление заказа")
    public void orderPhones(User user, OrderRequestDto orderRequestDto) {
        given()
                .header(user.getAuthHeader())
                .body(orderRequestDto)
                .post(getEndpoint())
                .then()
                .statusCode(200);
    }

}
