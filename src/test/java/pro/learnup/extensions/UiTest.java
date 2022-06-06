package pro.learnup.extensions;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.learnup.allure.Layer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Layer("UI Tests")
@ExtendWith({UiTestsExt.class, AllureJunit5.class, ApiTestExtension.class})
public @interface UiTest {
}
