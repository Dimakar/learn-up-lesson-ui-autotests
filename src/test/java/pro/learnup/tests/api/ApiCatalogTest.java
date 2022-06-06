package pro.learnup.tests.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.learnup.api.dto.PhoneDto;
import pro.learnup.api.endpoints.ApiCatalogEndpoint;
import pro.learnup.extensions.ApiTest;
import pro.learnup.testdata.DbTestDataHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("/api/catalog: Получение списка смартфонов")
@ApiTest
public class ApiCatalogTest {
    List<PhoneDto> phoneDtoList;

    @BeforeEach
    void setUp() {
        phoneDtoList = DbTestDataHelper.getAllPhones();
    }

    @Test
    @DisplayName("/api/catalog: 200, получение смартфонов без авторизации")
    void getCatalogTest() {
        assertThat(new ApiCatalogEndpoint().getAllPhones())
                .containsExactlyInAnyOrderElementsOf(phoneDtoList);
    }
}
