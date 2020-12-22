package task19.tests;

import org.junit.Test;
import task19.app.Application;

public class CartTest extends TestBase {

    @Test
    public void checkCart() {
        int countOfPurchases = 3;
        new Application().addProductToCart(countOfPurchases);
        new Application().deleteProductsFromCart();
        new Application().quit();
    }
}
