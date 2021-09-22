package com.webshop.webshop;

import com.webshop.webshop.interfaces.WebShopItem;

import java.util.*;

public class WebShop {
    private final Set<User> registeredUsers = new HashSet<>();
    private final Set<User> loginUser = new HashSet<>();
    private final Map<WebShopItem, Integer> availableProducts;
    private final List<Order> orders = new ArrayList<>();

    public WebShop(Map<WebShopItem, Integer> availableProducts) {
        this.availableProducts = availableProducts;
    }

    public boolean registerUser(String name) {
        boolean success = false;
        if (!hasRegisteredUser(name)) {
            registeredUsers.add(new User(registeredUsers.size() + 1, name));
            success = true;
        }
        return success;
    }

    public boolean loginUser(String name) {
        boolean success = false;
        if (hasRegisteredUser(name)) {
            for (User user : registeredUsers) {
                if (!loginUser.contains(user) && user.getName().equals(name)) {
                    loginUser.add(user);
                    success = true;
                }
            }
        }
        return success;
    }

    public boolean logoutUser(String name) {
        boolean success = false;
        for (User user : registeredUsers) {
            if (user.getName().equals(name)) {
                loginUser.remove(user);
                success = true;
            }
        }
        return success;
    }

    private boolean hasRegisteredUser(String name) {
        boolean success = false;

        for (User user : registeredUsers) {
            if (user.getName().equals(name)) {
                success = true;
                break;
            }
        }
        return success;
    }

    public User getLoggedUserByName(String name) {
        User user = null;
        for (User find : loginUser) {
            if (find.getName().equals(name)) {
                user = find;
                break;
            }
        }
        return user;
    }

    public User getRegisteredUserByName(String name) {
        User user = null;
        for (User find : registeredUsers) {
            if (find.getName().equals(name)) {
                user = find;
                break;
            }
        }
        return user;
    }

    public List<Order> getPreviousOrders(String name) {
        List<Order> userPreviousOrders = new ArrayList<>();
        User findUser = getLoggedUserByName(name);
        if (findUser != null) {
            for (Order storedOrder : orders) {
                if (storedOrder.getUserId() == findUser.getUserId()) {
                    userPreviousOrders.add(storedOrder);
                }
            }
        }
        return userPreviousOrders;
    }

    public void pay(String name) {
        User user = getLoggedUserByName(name);
        if (user != null) {
            Order order = user.getCart().pay(user.getUserId());
            orders.add(order);
        }
    }

    public boolean removeItemFromCustomerCart(WebShopItem item) {
        if (availableProducts.containsKey(item)) {
            availableProducts.put(item, availableProducts.get(item) + 1);
            return true;
        } else {
            return false;
        }
    }

    public boolean addItemToCustomerCart(WebShopItem item) {
        if (availableProducts.containsKey(item) && availableProducts.get(item) > 0) {
            availableProducts.put(item, availableProducts.get(item) - 1);
            return true;
        } else {
            return false;
        }
    }

    public Map<WebShopItem, Integer> getAvailableProducts() {
        return availableProducts;
    }
}
