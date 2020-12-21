package task13;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Cart {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void loginPositive() {
        driver.get("http://localhost/litecart/en/");

        for (int i = 1; i < 4; i++) {
            wait.until(visibilityOfElementLocated(By.id("slider")));

            //взял конкретный товар, так как с другими при добавлении в корзину появляется аллерт с ошибкой
            driver.findElement(By.xpath("//div[@id='box-campaigns']/.//li[starts-with(@class, 'product')]")).click();

            wait.until(visibilityOf(driver.findElement(By.name("add_cart_product"))));

            //добавил этот кусок чтобы в корзине было 3 товара
            if (i == 1) {
                new Select(driver.findElement(By.name("options[Size]"))).selectByValue("Small");
            } else if (i == 2) {
                new Select(driver.findElement(By.name("options[Size]"))).selectByValue("Medium");
            } else {
                new Select(driver.findElement(By.name("options[Size]"))).selectByValue("Large");
            }


            driver.findElement(By.name("add_cart_product")).click();

            wait.until(ExpectedConditions.attributeToBe(By.className("quantity"), "innerText", "" + i));
            driver.navigate().back();
            wait.until(visibilityOfElementLocated(By.id("slider")));
        }

        driver.findElement(By.xpath("//a[contains(text(), 'Checkout')]")).click();
        wait.until(visibilityOfElementLocated(By.id("order_confirmation-wrapper")));

        List<WebElement> elementList = driver.findElements(By.xpath("//td[@class='item']"));

        for (int i = 0; i < elementList.size(); i++) {
            if(i!=elementList.size()-1) {
                wait.until(elementToBeClickable(By.xpath("//li[@class='shortcut'][1]/a")));
                driver.findElement(By.xpath("//li[@class='shortcut'][1]/a")).click();
            }
            wait.until(elementToBeClickable(By.name("remove_cart_item")));
            driver.findElement(By.name("remove_cart_item")).click();
            if(i!=elementList.size()-1) wait.until(presenceOfElementLocated(By.id("order_confirmation-wrapper")));
        }
        wait.until(visibilityOfElementLocated(By.id("checkout-cart-wrapper")));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
