package com.webshop.webshop;

import com.webshop.webshop.interfaces.WebShopItem;

public class Product implements WebShopItem {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return  "Termék:\t" + name +
                "\tár:\t" + price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
