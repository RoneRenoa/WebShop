package com.webshop.dataloader;

import com.webshop.dataloader.interfaces.SaveProduct;
import com.webshop.webshop.interfaces.WebShopItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ProductSaveToFile implements SaveProduct {
    private String path;
    private static final String SEPARATOR = ";";

    public ProductSaveToFile(String path) {
        this.path = path;
    }

    @Override
    public void saveData(Map<WebShopItem, Integer> items) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (Map.Entry<WebShopItem, Integer> entry : items.entrySet()) {
                bufferedWriter.write(entry.getKey().getName()
                        + SEPARATOR + entry.getKey().getPrice()
                        + SEPARATOR + entry.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
