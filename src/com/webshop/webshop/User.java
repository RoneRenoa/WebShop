package com.webshop.webshop;

import com.webshop.webshop.interfaces.Cart;

import java.util.Objects;

public class User {
    private Cart cart;
    private int userId;
    private String name;


    public User(int userId, String name){
        this.userId = userId;
        this.name = name;
        cart = new ShoppingCart();
    }

    public String getName() {
        return name;
    }

    public Cart getCart(){
        return cart;
    }

    public int getUserId(){
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
