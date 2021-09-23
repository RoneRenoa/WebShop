package com.webshop;

import com.webshop.dataloader.*;
import com.webshop.dataloader.exceptions.SelectedMenuItemException;
import com.webshop.dataloader.interfaces.LoadProduct;
import com.webshop.dataloader.interfaces.SaveProduct;
import com.webshop.enums.LoggedMainPageCommand;
import com.webshop.enums.WelcomePageCommand;
import com.webshop.webshop.Order;
import com.webshop.webshop.User;
import com.webshop.webshop.WebShop;
import com.webshop.webshop.interfaces.Cart;
import com.webshop.webshop.interfaces.WebShopItem;

import java.util.List;
import java.util.Map;

public class WebShopEngine {

    private UserInterface userInterface = new UserInterface();
    private WebShop webShop;
    private boolean isRunning = true;
    private boolean isLogged = false;
    private User actuallyUser;
    private static final String PRODUCTS_DATA_PATH = "products.txt";
    private static final String USERS_DATA_PATH = "users.txt";

    public WebShopEngine() {
        LoadProduct dataLoader = new ProductLoaderFromFile(PRODUCTS_DATA_PATH);
        UserLoadFromFile userLoader = new UserLoadFromFile(USERS_DATA_PATH);
        webShop = new WebShop(dataLoader.loadData(), userLoader.load());
    }

    public void run() {
        userInterface.writeWelcomePage();
        while (isRunning) {
            if (!isLogged) {
                logoutMainMenu((WelcomePageCommand) userInterface.getUserCommand());
            } else {
                loggedMainMenu((LoggedMainPageCommand) userInterface.getUserCommand());
            }
        }
    }

    private void addToCart() {
        int selectedMenuItem = 0;
        do {
            try {
                userInterface.showProducts(webShop.getAvailableProducts());
                WebShopItem selectedProduct = null;
                selectedMenuItem = Integer.parseInt(userInterface.getInput());
                validateSelectedMenuItem(selectedMenuItem, webShop.getAvailableProducts().size() + 1, 1);
                selectedProduct = getItemByMenuNumber(selectedMenuItem, webShop.getAvailableProducts());

                if (selectedProduct != null) {
                    if(webShop.addItemToCustomerCart(selectedProduct)) {
                        User whoBuy = webShop.getLoggedUserByName(actuallyUser.getName());
                        Cart buyerCart = whoBuy.getCart();
                        buyerCart.addProductToCart(selectedProduct);
                        userInterface.successfulAddToCart(selectedProduct.getName());
                    } else {
                        userInterface.error("Nincs több a termékből");
                    }
                }
            } catch (NumberFormatException | SelectedMenuItemException e) {
                userInterface.error("Hiba: sorszámmal választhat terméket");
            }
        } while (selectedMenuItem != webShop.getAvailableProducts().size() + 1);
        userInterface.printLoggedPage();
    }

    private void deleteFromCart() {
        Cart myCart = webShop.getLoggedUserByName(actuallyUser.getName()).getCart();
        int selectedMenuItem = 0;
        do {
            userInterface.showMyCart(myCart.viewCart());
            try {
                WebShopItem selectedProduct;
                selectedMenuItem = Integer.parseInt(userInterface.getInput());
                validateSelectedMenuItem(selectedMenuItem, myCart.viewCart().size() + 1, 1);
                selectedProduct = getItemByMenuNumber(selectedMenuItem, myCart.viewCart());
                myCart.removeProductFromCart(selectedProduct);
                if(webShop.removeItemFromCustomerCart(selectedProduct)) {
                    userInterface.successfulDeleteFromCart(selectedProduct.getName());
                    userInterface.showMyCart(myCart.viewCart());
                } else {
                    userInterface.error("Nincs több a termékből");
                }
            } catch (NumberFormatException | SelectedMenuItemException e) {
                userInterface.error("Hiba: sorszámmal választhat terméket");
            }
        } while (selectedMenuItem != myCart.viewCart().size() + 1);
        userInterface.printLoggedPage();
    }


    private void pay() {
        if (!webShop.getLoggedUserByName(actuallyUser.getName()).getCart().viewCart().isEmpty()) {
            userInterface.payAndOrder();
            Order order = webShop.pay(actuallyUser.getName());
            saveProducts(webShop.getAvailableProducts());
            saveHistory(order);
        } else {
            userInterface.cartIsEmpty();
        }
        userInterface.printLoggedPage();
    }

    private void loggedMainMenu(LoggedMainPageCommand command) {
        switch (command) {
            case CART:
                deleteFromCart();
                break;
            case PRODUCTS:
                addToCart();
                break;
            case PAY:
                pay();
                break;
            case HISTORY:
                history();
                break;
            case LOGOUT:
                logout(actuallyUser.getName());
                break;
            case INVALID:
                userInterface.invalid();
                userInterface.printLoggedPage();
                break;
            default:
                userInterface.error("Helytelen parancsot kaptam a UserInterface-től");
        }
    }

    private void validateSelectedMenuItem(int selectedMenuItem, int maxBound, int minBound)
            throws SelectedMenuItemException {
        if (selectedMenuItem < minBound || selectedMenuItem > maxBound) {
            throw new SelectedMenuItemException("Csak a sorszámokat fogadom el!");
        }
    }

    private int validateStringToInt(String input) throws SelectedMenuItemException {
        int convert;
        try {
            convert = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new SelectedMenuItemException("Csak a sorszámokat fogadom el!");
        }
        return convert;
    }

    private void history() { //TODO refactor
        List<Order> history = webShop.getPreviousOrders(actuallyUser.getName());
        userInterface.showHistory(history);
        int selectedMenuItem = 0;
        do {
            try {
                selectedMenuItem = validateStringToInt(userInterface.getInput());
                validateSelectedMenuItem(selectedMenuItem, history.size(), history.size());
            } catch (SelectedMenuItemException e) {
                userInterface.error(e.getMessage());
            }
        } while (selectedMenuItem != history.size() + 1);
        userInterface.printLoggedPage();
    }

    private void logoutMainMenu(WelcomePageCommand command) {
        switch (command) {
            case REGISTRATION:
                userInterface.writeRegistration();
                String registrationName = userInterface.getInput();
                registration(registrationName);
                break;
            case LOGIN:
                userInterface.writeLoginName();
                String loginName = userInterface.getInput();
                login(loginName);
                break;
            case EXIT:
                userInterface.printFarewell();
                isRunning = false;
                break;
            case INVALID:
                userInterface.invalid();
                userInterface.writeWelcomePage();
                break;
            default:
                userInterface.error("Helytelen parancsot kaptam a UserInterface-től");
        }
    }

    private void login(String loginName) {
        if (webShop.loginUser(loginName)) {
            actuallyUser = webShop.getLoggedUserByName(loginName);
            userInterface.successfulLogin(loginName);
            isLogged = true;
            userInterface.printLoggedPage();
        } else {
            userInterface.printInvalidLogin();
            userInterface.writeWelcomePage();
        }
    }

    private void logout(String name) {
        isLogged = false;
        webShop.logoutUser(name);
        userInterface.printLogout(name);
    }

    private void registration(String registrationName) {
        if (webShop.registerUser(registrationName)) {
            userInterface.printSuccessfulRegistration(registrationName);
            userInterface.writeWelcomePage();
            new UserSaveToFile(USERS_DATA_PATH).save(webShop.getRegisteredUserByName(registrationName));
        } else {
            userInterface.printInvalidRegistration();
            userInterface.writeWelcomePage();
        }
    }

    private WebShopItem getItemByMenuNumber(int selectedItem, Map<WebShopItem, Integer> items) {
        int increment = 0;
        for (WebShopItem key : items.keySet()) {
            increment++;
            if (increment == selectedItem) {
                return key;
            }
        }
        return null;
    }

    private void saveProducts(Map<WebShopItem, Integer> items){
        SaveProduct dataSaver = new ProductSaveToFile(PRODUCTS_DATA_PATH);
        dataSaver.saveData(items);
    }

    private void saveHistory(Order order){
        OrderSave orderSave = new OrderSave();
        orderSave.saveOrder(order);
    }
}
