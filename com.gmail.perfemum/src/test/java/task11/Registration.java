package task11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.concurrent.TimeUnit;

public class Registration {
    WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @Test
    public void createNewUser() {
        driver.get("http://localhost/litecart/en/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String email = "test" + (int) (Math.random() * 99999) + "@email.com";
        String password = "qwerty";

        driver.findElement(By.xpath("//a[contains(text(), 'New customers click here')]")).click();

        WebElement customerForm = driver.findElement(By.name("customer_form"));
        customerForm.findElement(By.name("firstname")).sendKeys("Test");
        customerForm.findElement(By.name("lastname")).sendKeys("Testov");
        customerForm.findElement(By.name("address1")).sendKeys("United States");

        Select selectCountry = new Select(customerForm.findElement(By.name("country_code")));
        ((JavascriptExecutor)driver).executeScript("arguments[0].selectedIndex=224; arguments[0].dispatchEvent(new Event('change'))" , selectCountry);

// Не разблокируется поле сразу
//        Select selectZone = new Select(customerForm.findElement(By.xpath("//select[@name='zone_code']")));
//        selectZone.selectByVisibleText("Alabama");

        customerForm.findElement(By.name("postcode")).sendKeys("12345");
        customerForm.findElement(By.name("city")).sendKeys("Washington");
        customerForm.findElement(By.name("email")).sendKeys(email);
        customerForm.findElement(By.name("phone")).sendKeys("791234567890");

        customerForm.findElement(By.name("password")).sendKeys(password);
        customerForm.findElement(By.name("confirmed_password")).sendKeys(password);
        customerForm.findElement(By.name("create_account")).click();

        //Костыль, только после рефреша страницы поле "Zone/State/Province" разблокируется
        Select selectZone = new Select(driver.findElement(By.xpath("//select[@name='zone_code']")));
        selectZone.selectByVisibleText("Alaska");
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);
        driver.findElement(By.name("create_account")).click();
        //----

        driver.findElement(By.xpath("//a[contains(text(), 'Logout')]")).click();
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        driver.findElement(By.xpath("//a[contains(text(), 'Logout')]")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
