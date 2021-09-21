package com.webshop.webshop;

import com.webshop.webshop.interfaces.Cart;
import com.webshop.webshop.interfaces.WebShopItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart implements Cart {
    private List<WebShopItem> shoppingCart = new ArrayList<>();

    @Override
    public void addProductToCart(WebShopItem product) {
        shoppingCart.add(product);
    }

    @Override
    public void removeProductFromCart(WebShopItem product) {
        shoppingCart.remove(product);
    }

    @Override
    public Order pay(int userId) {
        Order order = new Order(shoppingCart, userId);
        shoppingCart.clear();
        return order;
    }

    @Override
    public List<WebShopItem> viewCart() {
        return Collections.unmodifiableList(shoppingCart);
    }
}
