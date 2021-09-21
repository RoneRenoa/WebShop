package com.webshop.webshop.interfaces;

import com.webshop.webshop.Order;

import java.util.List;

public interface Cart {
    public void addProductToCart(WebShopItem product);

    public void removeProductFromCart(WebShopItem product);

    public Order pay(int userId);

    public List<WebShopItem> viewCart();
}
