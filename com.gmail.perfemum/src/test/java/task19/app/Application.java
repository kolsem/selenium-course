package task19.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import task19.pages.CartPage;
import task19.pages.MainPage;

public class Application {
    private static WebDriver driver = new FirefoxDriver();
    
    private MainPage mainPage;
    private CartPage cartPage;

    public Application() {
        mainPage = new MainPage(driver);
        cartPage = new CartPage(driver);
    }

    public void addProductToCart(int count) {
        mainPage.open();
        mainPage.addProduct(count);
    }

    public void deleteProductsFromCart() {
        cartPage.goToCart();
        cartPage.deleteProducts();
    }


    public void quit() {
        driver.quit();
    }
}
