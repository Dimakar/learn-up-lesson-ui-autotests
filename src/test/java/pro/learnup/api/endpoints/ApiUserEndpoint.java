package pro.learnup.api.endpoints;

import io.qameta.allure.Step;
import pro.learnup.api.dto.UserDto;
import pro.learnup.testdata.User;

import static io.restassured.RestAssured.given;

@Endpoint("/api/user")
public class ApiUserEndpoint extends BaseEndpoint {

    @Step("{this.endpoint}: GET: 200: Получении информации об юзера {userDto.username}")
    public UserDto getUser(User user) {
        return given()
                .header(user.getAuthHeader())
                .get(getEndpoint())
                .then()
                .statusCode(200)
                .extract()
                .as(UserDto.class);
    }

}
