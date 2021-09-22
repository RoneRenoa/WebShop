package com.webshop.dataloader.interfaces;

import com.webshop.webshop.interfaces.WebShopItem;

import java.util.Map;

public interface SaveProduct {
    public void saveData(Map<WebShopItem, Integer> items);
}
