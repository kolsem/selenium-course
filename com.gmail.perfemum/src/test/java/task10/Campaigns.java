package task10;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;

public class Campaigns {
    WebDriver driver;

    @Before
    public void setUp() {
//        System.setProperty("webdriver.ie.driver", "driver/IEDriverServer.exe");
//        driver = new InternetExplorerDriver();
//        System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
//        driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public static void checkName(WebDriver driver) {
        String nameMainPage = driver.findElement(By.xpath("//div[@id='box-campaigns']/.//div[@class='name']")).getAttribute("innerText");
        driver.findElement(By.xpath("//div[@id='box-campaigns']/.//div[@class='name']")).click();
        String nameProductPage = driver.findElement(By.xpath("//h1[@class='title']")).getAttribute("innerText");
        assert (nameMainPage.equals(nameProductPage));
        driver.navigate().back();
    }

    public static void checkPrice(WebDriver driver) {
        String priceRegularMainPage = driver.findElement(By.xpath("//div[@id='box-campaigns']/.//s[@class='regular-price']")).getAttribute("innerText");
        String priceCampaignMainPage = driver.findElement(By.xpath("//div[@id='box-campaigns']/.//strong[@class='campaign-price']")).getAttribute("innerText");
        driver.findElement(By.xpath("//div[@id='box-campaigns']/.//div[@class='name']")).click();
        String priceRegularProductPage = driver.findElement(By.xpath("//s[@class='regular-price']")).getAttribute("innerText");
        String priceCampaignProductPage = driver.findElement(By.xpath("//strong[@class='campaign-price']")).getAttribute("innerText");
        assert (priceRegularMainPage.equals(priceRegularProductPage));
        assert (priceCampaignMainPage.equals(priceCampaignProductPage));
        driver.navigate().back();
    }

    public static void checkTextDecoration(String value){
        assert (value.startsWith("line-through"));
    }

    public static void checkFontWeight (String value){
        Assert.assertThat(value, anyOf(is("Bold"), is("700"), is("800"), is("900")));
    }

    public static void checkColor(String priceType, String value){
        String[] colorNumbers = value.replace("rgba(", "").replace(")", "").split(",");
        int r = Integer.parseInt(colorNumbers[0].trim());
        int g = Integer.parseInt(colorNumbers[1].trim());
        int b = Integer.parseInt(colorNumbers[2].trim());

        switch (priceType){
            case "regular":
                if (!(r == g && g == b)) {
                    throw new AssertionError();
                }
                break;
            case "campaign":
                if (!(r > g && (g + b) == 0)) {
                    throw new AssertionError();
                }
        }
    }

    @Test
    public void checkMenuSection() {
        driver.get("http://localhost/litecart/en/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        checkName(driver);
        checkPrice(driver);
        WebElement boxCampaigns = driver.findElement(By.xpath("//div[@id='box-campaigns']"));
        checkColor("regular",boxCampaigns.findElement(By.className("regular-price")).getCssValue("color"));
        checkTextDecoration(boxCampaigns.findElement(By.className("regular-price")).getCssValue("text-decoration"));
        checkColor("campaign",boxCampaigns.findElement(By.className("campaign-price")).getCssValue("color"));
        checkFontWeight(boxCampaigns.findElement(By.className("campaign-price")).getCssValue("font-weight"));

        float fontSizePriceRegularMainPage = Float.parseFloat(boxCampaigns.findElement(By.className("regular-price")).getCssValue("font-size").replace("px", ""));
        float fontSizePriceCampaignMainPage = Float.parseFloat(boxCampaigns.findElement(By.className("campaign-price")).getCssValue("font-size").replace("px", ""));
        assert (fontSizePriceRegularMainPage < fontSizePriceCampaignMainPage);

        boxCampaigns.findElement(By.className("name")).click();

        checkColor("regular",driver.findElement(By.className("regular-price")).getCssValue("color"));
        checkTextDecoration(driver.findElement(By.className("regular-price")).getCssValue("text-decoration"));
        checkColor("campaign",driver.findElement(By.className("campaign-price")).getCssValue("color"));
        checkFontWeight(driver.findElement(By.className("campaign-price")).getCssValue("font-weight"));

        float fontSizePriceRegularProductPage = Float.parseFloat(driver.findElement(By.className("regular-price")).getCssValue("font-size").replace("px", ""));
        float fontSizePriceCampaignProductPage = Float.parseFloat(driver.findElement(By.className("campaign-price")).getCssValue("font-size").replace("px", ""));
        assert (fontSizePriceRegularProductPage < fontSizePriceCampaignProductPage);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
