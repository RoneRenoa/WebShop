package com.webshop.webshop;

import com.webshop.webshop.interfaces.Cart;
import com.webshop.webshop.interfaces.WebShopItem;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart implements Cart {
    private Map<WebShopItem, Integer> shoppingCart = new LinkedHashMap<>();

    @Override
    public void addProductToCart(WebShopItem product) {
        if (shoppingCart.containsKey(product)) {
            shoppingCart.put(product, shoppingCart.get(product) + 1);
        } else {
            shoppingCart.put(product, 1);
        }
    }

    @Override
    public void removeProductFromCart(WebShopItem product) {
        if (shoppingCart.containsKey(product)) {
            if (shoppingCart.get(product) - 1 <= 0) {
                shoppingCart.remove(product);
            } else {
                shoppingCart.put(product, shoppingCart.get(product) - 1);
            }
        }
    }

    @Override
    public Order pay(int userId) {
        Order order = new Order(shoppingCart, userId);
        shoppingCart.clear();
        return order;
    }

    @Override
    public Map<WebShopItem, Integer> viewCart() {
        return shoppingCart;
    }
}
