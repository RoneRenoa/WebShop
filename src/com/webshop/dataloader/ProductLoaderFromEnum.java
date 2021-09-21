package com.webshop.dataloader;

import com.webshop.webshop.Product;
import com.webshop.webshop.interfaces.WebShopItem;
import com.webshop.dataloader.interfaces.DataLoader;
import com.webshop.enums.PredefinedProducts;

import java.util.ArrayList;
import java.util.List;

public class ProductLoaderFromEnum implements DataLoader {

    @Override
    public List<WebShopItem> loadData() {
        List<WebShopItem> products = new ArrayList<>();
        for(PredefinedProducts enums : PredefinedProducts.values()){
            products.add(new Product(enums.productValue, enums.price));
        }
        return products;
    }
}
