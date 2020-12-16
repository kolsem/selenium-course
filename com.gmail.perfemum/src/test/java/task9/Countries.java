package task9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Countries {
    WebDriver driver;

    String credentials = "admin";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void checkCountries() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys(credentials);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(credentials);
        driver.findElement(By.xpath("//button[@name='login']")).click();

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        List<WebElement> countriesWebElements = driver.findElements(By.xpath("//tr[@class='row']/./td/./a[not(@title)]"));
        List<String> countriesName = new ArrayList<>();
        List<String> countriesNameSorted = new ArrayList<>();

        for (WebElement element : countriesWebElements) {
            countriesName.add(element.getText());
            countriesNameSorted.add(element.getText());
        }

        Collections.sort(countriesNameSorted);

        assert (countriesName.equals(countriesNameSorted));

        List<WebElement> countryWithNotZeroZones = driver.findElements(By.xpath("//tr[@class='row']/./td[6]"));
        List<Integer> index = new ArrayList<>();
        List<String> zonesName;
        List<String> zonesNameSorted;

        for (int i = 0; i < countryWithNotZeroZones.size(); i++) {
            if (!countryWithNotZeroZones.get(i).getText().equals("0")) {
                index.add(i);
            }
        }

        for (Integer integer : index) {
            driver.findElements(By.xpath("//td[5]/a")).get(integer).click();
            List<WebElement> zeroZones = driver.findElements(By.xpath("//td[input[@type='hidden']][3]"));
            zonesName = new ArrayList<>();
            zonesNameSorted = new ArrayList<>();
            for (WebElement zones : zeroZones) {
                zonesName.add(zones.getAttribute("textContent"));
                zonesNameSorted.add(zones.getAttribute("textContent"));
            }

            Collections.sort(zonesNameSorted);
            assert (zonesName.equals(zonesNameSorted));
            driver.navigate().back();
        }
    }

    @Test
    public void checkGeoZones() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys(credentials);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(credentials);
        driver.findElement(By.xpath("//button[@name='login']")).click();

        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        int zoneCount = driver.findElements(By.xpath("//tr[@class='row']")).size();
        List<WebElement> zonesWebElement;
        List<String> zonesName;
        List<String> zonesNameSorted;

        while (zoneCount > 0) {
            zoneCount--;
            List<WebElement> zones = driver.findElements(By.xpath("//td[3]/a"));
            zones.get(zoneCount).click();
            zonesWebElement = driver.findElements(By.xpath("//td[3]/select/option[@selected='selected']"));
            zonesName = new ArrayList<>();
            zonesNameSorted = new ArrayList<>();
            for (WebElement element : zonesWebElement) {
                zonesName.add(element.getAttribute("textContent"));
                zonesNameSorted.add(element.getAttribute("textContent"));
            }

            Collections.sort(zonesNameSorted);
            assert (zonesName.equals(zonesNameSorted));
            driver.navigate().back();
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
