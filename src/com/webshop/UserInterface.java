package com.webshop;

import com.webshop.enums.interfaces.EnumCommand;
import com.webshop.webshop.Order;
import com.webshop.webshop.commands.LoggedInUiCommand;
import com.webshop.webshop.commands.LoggedOutUiCommand;
import com.webshop.webshop.interfaces.Command;
import com.webshop.webshop.interfaces.WebShopItem;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner = new Scanner(System.in);
    private Command uiCommand;

    public UserInterface() {
        uiCommand = new LoggedOutUiCommand(scanner);
    }

    public EnumCommand getUserCommand() {
        return uiCommand.getCommand();
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void setUICommand(Command uiCommand) {
        this.uiCommand = uiCommand;
    }

    public void writeWelcomePage() {
        System.out.println("""
                Üdvözlöm Pisti online pékségében
                Kérem jelentkezzen be, vagy készítsen új fiókot!
                Válassz egy menüpontot:
                1. Regisztáció
                2. Belépés
                3. Kilépés
                """);
    }

    public void printLoggedPage() {
        System.out.println("""
                Válasszon menüpontok közül
                1. - Termékek böngészése
                2. - Kosaram tartalma
                3. - Fizetés
                4. - Korábbi rendeléseim
                5. - Kijelentkezés
                """);
    }

    public void writeLoginName() {
        System.out.println("Adja meg a felhasználónevét: \n");
    }

    public void printFarewell() {
        System.out.println("""
                Köszönjük a vásárlást!
                Jó étvágyat!
                """);
        scanner.close();
    }

    public void writeRegistration() {
        System.out.println("Adja meg a felhasználónevét: \n");
    }

    public void printInvalidRegistration() {
        System.out.println("""
                Már regisztráltak ilyen névvel.
                Kérem válasszon másik felhasználónevet
                """);
    }

    public void printInvalidLogin() {
        System.out.println("Érvénytelen felhasználónév!\n");
    }

    public void printSuccessfulRegistration(String name) {
        System.out.println("Sikeres regisztráció: " + name);
    }

    public void successfulLogin(String name) {
        this.setUICommand(new LoggedInUiCommand(scanner));
        System.out.println("Üdv " + name + "!\n");
    }

    public void printLogout(String name) {
        System.out.println(name + " kijelentkezett");
        writeWelcomePage();
        uiCommand = new LoggedOutUiCommand(scanner);
    }

    public void error(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void invalid() {
        System.out.println("Érvénytelen parancs");
    }

    public void showProducts(List<WebShopItem> availableProducts) {
        System.out.println("A sorszámot beírva választhat elérhető termékeink közül:\n");
        int i = 1;
        for (WebShopItem item : availableProducts) {
            System.out.println(i + ".\t" + item);
            i++;
        }
        System.out.println(i + ".\tVissza");
    }

    public void successfulAddToCart(String name) {
        System.out.println(name + " hozzáadva a kosarához");
    }

    public void showMyCart(List<WebShopItem> viewCart) {
        StringBuilder myPurchases = new StringBuilder();
        myPurchases.append("A termék sorszámával tudja azt törölni a kosárból\n");
        myPurchases.append("A kosár tartalma:\n");
        int i = 1;
        for (WebShopItem item : viewCart) {
            myPurchases.append(i + ". " + item + "\n");
            i++;
        }
        myPurchases.append(i + ". Vissza");
        System.out.println(myPurchases);
    }

    public void successfulDeleteFromCart(String name) {
        System.out.println("Kivetted a kosárból: " + name);
    }

    public void payAndOrder() {
        System.out.println("Cuccos megrendelve, kifizetve");
    }

    public void cartIsEmpty() {
        System.out.println("Kosár üres");
    }

    public void showHistory(List<Order> history) {
        System.out.println("Csak visszalépni tud");
        System.out.println("Korábbi vásárlásai:");
        int i = 0;
        for (Order order : history) {
            i++;
            System.out.println(i + ". rendelés");
            for (WebShopItem item : order.getPreviousProducts()) {
                System.out.println(item.getName());
            }
            System.out.println();
        }
        System.out.println((i + 1) + ". Vissza");
    }
}
