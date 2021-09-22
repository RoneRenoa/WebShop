package com.webshop.dataloader;

import com.webshop.dataloader.interfaces.LoadProduct;
import com.webshop.webshop.Product;
import com.webshop.webshop.interfaces.WebShopItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductLoaderFromFile implements LoadProduct {
    private String path;
    private static final String SEPARATOR = ";";

    public ProductLoaderFromFile(String path) {
        this.path = path;
    }

    @Override
    public Map<WebShopItem, Integer> loadData() {
        Map<WebShopItem, Integer> products = new LinkedHashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] elements = line.split(SEPARATOR);
                products.put(new Product(elements[0], Double.parseDouble(elements[1])), Integer.parseInt(elements[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
