package task14;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class Links {
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

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.xpath("//a[text()='Afghanistan']")).click();

        List<WebElement> urlList = driver.findElements(By.xpath("//i[@class='fa fa-external-link']"));
        String mainWindow = driver.getWindowHandle();

        for (WebElement url : urlList) {
            url.click();
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                }
            }
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
