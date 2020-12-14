import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Authorization {

    WebDriver driver;
    WebDriverWait wait;

    By usernameField = By.xpath("//input[@name='username']");
    By passwordField = By.xpath("//input[@name='password']");
    By loginButton = By.xpath("//button[@name='login']");

    String credentials = "admin";

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void loginPositive(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(usernameField).sendKeys(credentials);
        driver.findElement(passwordField).sendKeys(credentials);
        driver.findElement(loginButton).click();
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
