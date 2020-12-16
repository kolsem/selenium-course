import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPage {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void checkMenuSection() {
        driver.get("http://localhost/litecart/en/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        List<WebElement> ducks = driver.findElements(By.xpath("//li[@class='product column shadow hover-light']"));

        for (WebElement element : ducks){
            assert (element.findElements(By.cssSelector(".sticker")).size()==1);
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
