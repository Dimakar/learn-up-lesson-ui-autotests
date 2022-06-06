package pro.learnup.tests.api;

import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pro.learnup.api.dto.UserDto;
import pro.learnup.api.endpoints.ApiAuthRegisterEndpoint;
import pro.learnup.extensions.ApiTest;
import pro.learnup.testdata.ApiTestDataHelper;
import pro.learnup.testdata.DbTestDataHelper;

import java.util.stream.Stream;

import static io.qameta.allure.Allure.parameter;
import static io.restassured.RestAssured.given;

@DisplayName("/api/auth/register: Регистрация нового пользователя")
@ApiTest
public class ApiAuthRegisterTest {
    static Faker faker = new Faker();
    UserDto userDto;

    public static Stream<UserDto> successfulCreateUserRequests() {
        return Stream.of(UserDto.builder()
                        .username(faker.name().fullName())
                        .password(faker.internet().password())
                        .build(),
                UserDto.builder()
                        .address(faker.address().fullAddress())
                        .phone(faker.phoneNumber().phoneNumber())
                        .email(faker.internet().emailAddress())
                        .username(faker.name().fullName())
                        .password(faker.internet().password())
                        .build()
        );
    }

    public static Stream<UserDto> failedCreateUserRequests() {
        return Stream.of(UserDto.builder()
                        .password(faker.internet().password())
                        .build(),
                UserDto.builder()
                        .username(faker.name().fullName())
                        .build()
        );
    }

    @ParameterizedTest
    @DisplayName("/api/auth/register: 201: успешное создание юзера")
    @MethodSource("successfulCreateUserRequests")
    void createUserTest(UserDto userDto) {
        parameter("Юзер", userDto);
        this.userDto = new ApiAuthRegisterEndpoint().registerNewUser(userDto);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(this.userDto)
                .as("Созданный юзер должен быть равен ожидаемому")
                .usingRecursiveComparison()
                .ignoringFields("id", "orders", "password", "token")
                .isEqualTo(this.userDto);
        softAssertions.assertThat(this.userDto.getId().toString()).isNotEmpty();
        softAssertions.assertThat(this.userDto.getPassword()).isNotEmpty();
        softAssertions.assertThat(this.userDto.getToken()).isNotEmpty();
        softAssertions.assertThat(this.userDto.getOrders()).isEmpty();
        softAssertions.assertAll();
    }

    @ParameterizedTest
    @DisplayName("/api/auth/register: 400: неуспешное создание юзера")
    @MethodSource("failedCreateUserRequests")
    void failedCreateUser400Test(UserDto userDto) {
        parameter("Юзер", userDto);
        given()
                .body(userDto)
                .post(new ApiAuthRegisterEndpoint().getEndpoint())
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("/api/auth/register: 409: User already exists")
    void failedCreateUser409Test() {
        userDto = ApiTestDataHelper.createTestUserDto();

        given()
                .body(userDto)
                .post(new ApiAuthRegisterEndpoint().getEndpoint())
                .then()
                .statusCode(409)
                .body("message", Matchers.equalTo("User already exists"));
    }

    @AfterEach
    void tearDown() {
        DbTestDataHelper.deleteUser(userDto);
    }
}
