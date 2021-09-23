package com.webshop.webshop;

import com.webshop.webshop.interfaces.WebShopItem;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
    private Map<WebShopItem, Integer> previousProducts = new LinkedHashMap<>();
    private int userId;
    private int orderId;

    public Map<WebShopItem, Integer> getPreviousProducts() {
        return previousProducts;
    }

    public int getUserId() {
        return userId;
    }

    public Order(Map<WebShopItem, Integer> previousProducts, int userId) {
        this.previousProducts.putAll(previousProducts);
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Korábbi rendelések:\n");
        for (Map.Entry entry : previousProducts.entrySet()) {
            result.append("\t" + entry.getKey() + "\t" + entry.getValue() + " db\n");
        }
        return result.toString();
    }
}
