package pro.learnup.tests.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.learnup.api.dto.UserDto;
import pro.learnup.api.endpoints.ApiUserEndpoint;
import pro.learnup.extensions.ApiTest;
import pro.learnup.testdata.ApiTestDataHelper;
import pro.learnup.testdata.DbTestDataHelper;
import pro.learnup.testdata.User;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("/api/user: Пользователи")
@ApiTest
public class ApiUserTest {
    UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = ApiTestDataHelper.createTestUserDto();
    }

    @Test
    @DisplayName("/api/user: 200: получение информации о юзере авторизованным пользователем")
    void successfulGetUserTest() {
        assertThat(new ApiUserEndpoint().getUser(User.builder().token(userDto.getToken()).build()))
                .usingRecursiveComparison()
                .isEqualTo(userDto);
    }

    @AfterEach
    void tearDown() {
        DbTestDataHelper.deleteUser(userDto);
    }
}
