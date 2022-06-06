package pro.learnup.config;

import org.aeonbits.owner.Config;


@Config.Sources("file:src/test/resources/stageconfig.properties")
public interface TestConfig extends Config {
    String baseUrl();

    String baseUri();

    String dbConnection();

    String dbName();

    @DefaultValue("2000")
    long timeout();

    @Key("webdriver.url")
    String selenoidUrl();

    @DefaultValue("true")
    boolean enableVNC();

    @DefaultValue("false")
    boolean enableVideo();

    @DefaultValue("chrome")
    String browser();

    @DefaultValue("101.0")
    String browserVersion();
}
