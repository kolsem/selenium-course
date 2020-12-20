package task12;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class newProduct {
    WebDriver driver;
    WebDriverWait wait;

    By usernameField = By.xpath("//input[@name='username']");
    By passwordField = By.xpath("//input[@name='password']");
    By loginButton = By.xpath("//button[@name='login']");

    String credentials = "admin";

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void addNewProduct() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(usernameField).sendKeys(credentials);
        driver.findElement(passwordField).sendKeys(credentials);
        driver.findElement(loginButton).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Catalog']"))); //с Firefox работает отлично, с Chrome как то через раз возможно из-за окружения
        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        driver.findElement(By.xpath("//a[text()=' Add New Product']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='status' and @value='1']")));
        driver.findElement(By.xpath("//input[@name='status' and @value='1']")).click();
        String name = "Duck" + (int) (Math.random() * 99999);
        String code = "" + (int) (Math.random() * 99999);
        driver.findElement(By.name("name[en]")).sendKeys(name);
        driver.findElement(By.name("code")).sendKeys(code);
        driver.findElement(By.xpath("//input[@name='product_groups[]' and @value='1-3']")).click();
        driver.findElement(By.name("quantity")).clear();
        driver.findElement(By.name("quantity")).sendKeys("15");
        System.out.println(new File("src/test/resources/greenDuck.png").getAbsolutePath());
        driver.findElement(By.name("new_images[]")).sendKeys(new File("src/test/resources/greenDuck.png").getAbsolutePath());
        driver.findElement(By.name("date_valid_from")).sendKeys("20/12/2020");
        driver.findElement(By.name("date_valid_to")).sendKeys("20/12/2021");


        driver.findElement(By.xpath("//a[text()='Information']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("manufacturer_id")));
        new Select(driver.findElement(By.name("manufacturer_id"))).selectByValue("1");
        driver.findElement(By.name("keywords")).sendKeys("Duck");
        driver.findElement(By.name("short_description[en]")).sendKeys("Green Duck");
        new Actions(driver).moveToElement(driver.findElement(By.className("trumbowyg-editor"))).click().sendKeys("Little Green Duck").build().perform();
        driver.findElement(By.name("head_title[en]")).sendKeys("Duck");
        driver.findElement(By.name("meta_description[en]")).sendKeys("Duck");


        driver.findElement(By.xpath("//a[text()='Prices']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("purchase_price")));
        driver.findElement(By.name("purchase_price")).clear();
        driver.findElement(By.name("purchase_price")).sendKeys("2");
        new Select(driver.findElement(By.name("purchase_price_currency_code"))).selectByValue("USD");
        driver.findElement(By.name("gross_prices[USD]")).clear();
        driver.findElement(By.name("gross_prices[USD]")).sendKeys("2");
        driver.findElement(By.name("gross_prices[EUR]")).clear();
        driver.findElement(By.name("gross_prices[EUR]")).sendKeys("1.8");
        driver.findElement(By.name("save")).click();


        List<WebElement> allProducts = driver.findElements(By.xpath("//tr[@class='row']/td[3]/a"));

        for (WebElement element: allProducts) {
            if (name.equals(element.getAttribute("innerText"))) break;
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
