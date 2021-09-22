package com.webshop.webshop.interfaces;

import com.webshop.webshop.Order;

import java.util.Map;

public interface Cart {
    public void addProductToCart(WebShopItem product);

    public void removeProductFromCart(WebShopItem product);

    public Order pay(int userId);

    public Map<WebShopItem, Integer> viewCart();
}
