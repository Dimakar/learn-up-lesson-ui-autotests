package pro.learnup.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CartPage extends BasePage {

    @FindBy(xpath = "//button[.='Checkout']")
    private WebElement checkOutButton;

    @FindBy(xpath = "//button[.='Confirm']")
    private WebElement confirmButton;

    public CartPage(WebDriver webDriver) {
        super(webDriver);
    }

    public CartPage checkCartContainExactly(String... phoneName) {
        List<String> actualPhoneList = webDriver.findElement(By.className("cart-items"))
                .findElements(By.xpath(".//table//tbody/tr"))
                .stream()
                .map(el -> el.findElements(By.xpath("./td")).get(1).getText())
                .collect(Collectors.toList());

        assertThat(actualPhoneList).containsExactlyInAnyOrder(phoneName);
        return this;
    }

    public CartPage clickCheckOut() {
        checkOutButton.click();
        return new CartPage(webDriver);
    }

    public CartPage clickConfirm() {
        confirmButton.click();
        return new CartPage(webDriver);
    }


    public CartPage checkThatCheckOutIsSuccessful() {
        new WebDriverWait(webDriver, 3).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[text()=\"Your order has been received. The items you've ordered will be sent to your address.\"]")));
        return new CartPage(webDriver);
    }
}