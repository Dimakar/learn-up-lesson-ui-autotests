package pro.learnup.extensions;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.remote.DesiredCapabilities;
import pro.learnup.config.TestConfig;

import java.util.List;
import java.util.logging.Level;

public class UiTestsExt implements AfterEachCallback, BeforeAllCallback {

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        Selenide.clearBrowserLocalStorage();
        Selenide.clearBrowserCookies();
        WebDriverRunner.clearBrowserCache();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        TestConfig config = ConfigFactory.create(TestConfig.class);
        SelenideLogger.addListener("Allure", new AllureSelenide().screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(false)
                .enableLogs(LogType.BROWSER, Level.ALL)
                .enableLogs(LogType.SERVER, Level.ALL));
        Configuration.baseUrl = config.baseUrl();
        Configuration.timeout = config.timeout();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (config.selenoidUrl() != null) {
            capabilities.setCapability("enableVNC", config.enableVNC());
            capabilities.setCapability("enableVideo", config.enableVideo());
            capabilities.setCapability("sessionTimeout", "20m");
            capabilities.setCapability("env", List.of(
                    "LANG=ru_RU.UTF-8",
                    "LANGUAGE=ru:RU",
                    "LC_ALL=ru_RU.UTF-8"));
            Configuration.remote = config.selenoidUrl();
        }
        Configuration.browserCapabilities = capabilities;
        Configuration.browserSize = "1366x768";
        Configuration.browser = config.browser();
        Configuration.browserVersion = config.browserVersion();
    }
}
