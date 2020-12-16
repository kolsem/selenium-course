import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Navigation {
    WebDriver driver;
    WebDriverWait wait;

    String credentials = "admin";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkMenuSection() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys(credentials);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(credentials);
        driver.findElement(By.xpath("//button[@name='login']")).click();

        int menuSize = driver.findElements(By.xpath("//li[@id='app-']")).size();

        while (menuSize > 0) {
            menuSize--;
            List<WebElement> menuSections = driver.findElements(By.xpath("//li[@id='app-']"));
            menuSections.get(menuSize).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));

            int subMenuSize = driver.findElements(By.xpath("//li[contains(@id, 'doc-')]")).size();
            while (subMenuSize > 0) {
                subMenuSize--;
                List<WebElement> subMenuSections = driver.findElements(By.xpath("//li[contains(@id, 'doc-')]"));
                subMenuSections.get(subMenuSize).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
            }
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
