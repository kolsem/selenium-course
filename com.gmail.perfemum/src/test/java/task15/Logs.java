package task15;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Logs {
    WebDriver driver;
    WebDriverWait wait;

    By usernameField = By.xpath("//input[@name='username']");
    By passwordField = By.xpath("//input[@name='password']");
    By loginButton = By.xpath("//button[@name='login']");

    String credentials = "admin";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkLinks() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(usernameField).sendKeys(credentials);
        driver.findElement(passwordField).sendKeys(credentials);
        driver.findElement(loginButton).click();

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=2");

        List<WebElement> allProducts = driver.findElements(By.xpath("//tr[@class='row'][position()>3]/./td[3]/a"));
        for (int i=0;i < allProducts.size(); i++) {
            allProducts.get(i).click();
            driver.navigate().back();
            List<LogEntry> logs = driver.manage().logs().get(LogType.BROWSER).getAll();
            if (logs.size() > 0) {
                logs.forEach(System.out::println);
            }
            Assert.assertEquals(0, logs.size());
            allProducts = driver.findElements(By.xpath("//tr[@class='row'][position()>3]/./td[3]/a"));
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
