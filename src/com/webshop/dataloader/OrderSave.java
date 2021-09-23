package com.webshop.dataloader;

import com.webshop.webshop.Order;
import com.webshop.webshop.interfaces.WebShopItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class OrderSave {
    private static final String SEPARATOR = ";";

    public void saveOrder(Order order) {
        String fileName = order.getUserId() + ".txt";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Map.Entry<WebShopItem, Integer> entry : order.getPreviousProducts().entrySet()) {
                bufferedWriter.write(entry.getKey().getName()
                        + SEPARATOR + entry.getKey().getPrice()
                        + SEPARATOR + entry.getValue());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
