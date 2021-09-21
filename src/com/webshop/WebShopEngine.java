package com.webshop;

import com.webshop.dataloader.ProductLoaderFromEnum;
import com.webshop.dataloader.exceptions.SelectedMenuItemException;
import com.webshop.enums.LoggedMainPageCommand;
import com.webshop.enums.WelcomePageCommand;
import com.webshop.webshop.Order;
import com.webshop.webshop.User;
import com.webshop.webshop.WebShop;
import com.webshop.webshop.interfaces.Cart;
import com.webshop.webshop.interfaces.WebShopItem;

import java.util.List;

public class WebShopEngine {

    private UserInterface userInterface = new UserInterface();
    private WebShop webShop = new WebShop(new ProductLoaderFromEnum());
    private boolean isRunning = true;
    private boolean isLogged = false;
    private User actuallyUser;

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
            userInterface.showProducts(webShop.getAvailableProducts());
            try {
                WebShopItem selectedProduct = null;
                selectedMenuItem = validateStringToInt(userInterface.getInput());
                validateSelectedMenuItem(selectedMenuItem, webShop.getAvailableProducts().size(), 1);
                selectedProduct = webShop.getAvailableProducts().get(selectedMenuItem - 1);
                User whoBuy = webShop.getUserByName(actuallyUser.getName());
                Cart buyerCart = whoBuy.getCart();
                buyerCart.addProductToCart(selectedProduct);
                userInterface.successfulAddToCart(selectedProduct.getName());
                userInterface.showProducts(webShop.getAvailableProducts());
            } catch (SelectedMenuItemException | NumberFormatException e) {
                userInterface.error(e.getMessage());
            }
        } while (selectedMenuItem != webShop.getAvailableProducts().size() + 1);
        userInterface.printLoggedPage();
    }

    private void deleteFromCart() {
        Cart myCart = webShop.getUserByName(actuallyUser.getName()).getCart();
        int selectedMenuItem = 0;
        do {
            userInterface.showMyCart(myCart.viewCart());
            try {
                WebShopItem selectedProduct = null;
                selectedMenuItem = validateStringToInt(userInterface.getInput());
                validateSelectedMenuItem(selectedMenuItem, myCart.viewCart().size(), 1);
                selectedProduct = myCart.viewCart().get(selectedMenuItem - 1);
                myCart.removeProductFromCart(selectedProduct);
                userInterface.successfulDeleteFromCart(selectedProduct.getName());
                userInterface.showMyCart(myCart.viewCart());
            } catch (SelectedMenuItemException e) {
                userInterface.error(e.getMessage());
            }
        } while (selectedMenuItem != myCart.viewCart().size() + 1);
        userInterface.printLoggedPage();
    }


    private void pay() {
        if (!webShop.getUserByName(actuallyUser.getName()).getCart().viewCart().isEmpty()) {
            userInterface.payAndOrder();
            webShop.pay(actuallyUser.getName());
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

    private void validateSelectedMenuItem(int selectedMenuItem, int maxBound, int minBound)  //TODO refactor
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
            actuallyUser = webShop.getUserByName(loginName);
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
        } else {
            userInterface.printInvalidRegistration();
            userInterface.writeWelcomePage();
        }
    }
}
