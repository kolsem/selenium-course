package task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "options[Size]")
    WebElement size;

    @FindBy(xpath = "//div[@id='box-campaigns']/.//li[starts-with(@class, 'product')]")
    public WebElement product;

    @FindBy(xpath = "//a[contains(text(), 'Checkout')]")
    WebElement cart;

    @FindBy(name = "add_cart_product")
    WebElement addButton;

    By slider = By.id("slider");
    By quantity = By.className("quantity");

    public String url = "http://localhost/litecart/";

    public void open() {
        driver.get(url);
    }

    public void addProduct(int count) {
        for (int i = 1; i < count + 1; i++) {
            wait.until(visibilityOfElementLocated(slider));

            //взял конкретный товар, так как с другими при добавлении в корзину появляется аллерт с ошибкой
            product.click();

            wait.until(visibilityOf(addButton));

            //добавил этот кусок чтобы в корзине было 3 товара
            if (i == 1) {
                new Select(size).selectByValue("Small");
            } else if (i == 2) {
                new Select(size).selectByValue("Medium");
            } else {
                new Select(size).selectByValue("Large");
            }


            addButton.click();

            wait.until(ExpectedConditions.attributeToBe(quantity, "innerText", "" + i));
            driver.navigate().back();
            wait.until(visibilityOfElementLocated(slider));
        }
    }
}
