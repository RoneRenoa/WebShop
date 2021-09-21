package com.webshop.webshop;

import com.webshop.webshop.interfaces.WebShopItem;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<WebShopItem> previousProducts = new ArrayList<>();
    private int userId;

    public List<WebShopItem> getPreviousProducts() {
        return previousProducts;
    }

    public int getUserId() {
        return userId;
    }

    public Order(List<WebShopItem> previousProducts, int userId) {
        this.previousProducts.addAll(previousProducts);
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Korábbi rendelések:\n");
        for (WebShopItem products : previousProducts) {
            result.append(" " + products + "\n");
        }
        return result.toString();
    }
}
