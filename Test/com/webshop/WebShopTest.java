package com.webshop;

import com.webshop.dataloader.ProductLoaderFromFile;
import com.webshop.webshop.Order;
import com.webshop.webshop.Product;
import com.webshop.webshop.User;
import com.webshop.webshop.WebShop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebShopTest {
    private List<Product> productList = List.of(new Product("Szőnyeg", 5000));
    private WebShop webshop;

    @BeforeEach
    void setUp() {
        webshop = new WebShop(new ProductLoaderFromFile("products.txt").loadData());
    }

    @Test
    void registration_userDontExist() {
        boolean success = webshop.registerUser("Magu");
        assertTrue(success);
    }

    @Test
    void registration_userExist() {
        webshop.registerUser("Magu");
        boolean success = webshop.registerUser("Magu");
        assertFalse(success);
    }

    @Test
    void loginUserIfNotExist() {
        boolean success = webshop.loginUser("Magu");
        assertFalse(success);
    }

    @Test
    void loginUserIfExist() {
        webshop.registerUser("Magu");
        boolean success = webshop.loginUser("Magu");
        assertTrue(success);
    }

    @Test
    void loginUserIfExistTwice() {
        webshop.registerUser("Magu");
        webshop.loginUser("Magu");
        boolean success = webshop.loginUser("Magu");
        assertFalse(success);
    }

    @Test
    void logout() {
        webshop.registerUser("Magu");
        webshop.loginUser("Magu");
        boolean success = webshop.logoutUser("Magu");
        assertTrue(success);
    }

    @Test
    void findUserNotLogged() {
        User user = webshop.getLoggedUserByName("Magu");
        assertEquals(null, user);
    }

    @Test
    void findUserLogged() {
        webshop.registerUser("Magu");
        webshop.loginUser("Magu");
        User user = webshop.getLoggedUserByName("Magu");
        user.getCart().addProductToCart(new Product("Szőnyeg", 3000));
        webshop.pay("Magu");
        webshop.logoutUser("Magu");
        User userAfterLogout = webshop.getLoggedUserByName("Magu");
        assertEquals(null, userAfterLogout);
    }

    @Test
    void getOrderAfterLogout(){
        webshop.registerUser("Magu");
        webshop.loginUser("Magu");
        User user = webshop.getLoggedUserByName("Magu");
        user.getCart().addProductToCart(new Product("Szőnyeg", 3000));
        webshop.pay("Magu");
        webshop.logoutUser("Magu");
        webshop.getLoggedUserByName("Magu");
        List<Order> orders = webshop.getPreviousOrders("Magu");
        assertTrue(orders.isEmpty());
    }
}