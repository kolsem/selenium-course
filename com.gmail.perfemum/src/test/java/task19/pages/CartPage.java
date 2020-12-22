package task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CartPage extends Page {
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "remove_cart_item")
    WebElement removeButton;

    @FindBy(xpath = "//a[contains(text(), 'Checkout')]")
    WebElement checkout;

    @FindBy(xpath = "//li[@class='shortcut'][1]/a")
    WebElement firstIcon;

    By table = By.id("order_confirmation-wrapper");
    By items = By.xpath("//td[@class='item']");
    By firstIconXPath = By.xpath("//li[@class='shortcut'][1]/a");
    By removeButtonXPath = By.name("remove_cart_item");
    By orderConfirmationXPath = By.id("order_confirmation-wrapper");
    By checkoutCartXPath = By.id("checkout-cart-wrapper");

    public void goToCart() {
        checkout.click();
        wait.until(visibilityOfElementLocated(table));
    }

    public void deleteProducts() {
        List<WebElement> elementList = driver.findElements(items);

        for (int i = 0; i < elementList.size(); i++) {
            if (i != elementList.size() - 1) {
                wait.until(elementToBeClickable(firstIconXPath));
                firstIcon.click();
            }
            wait.until(elementToBeClickable(removeButtonXPath));
            removeButton.click();
            if (i != elementList.size() - 1) wait.until(presenceOfElementLocated(orderConfirmationXPath));
        }
        wait.until(visibilityOfElementLocated(checkoutCartXPath));
    }
}
