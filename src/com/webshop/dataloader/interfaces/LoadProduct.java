package com.webshop.dataloader.interfaces;

import com.webshop.webshop.interfaces.WebShopItem;

import java.util.Map;

public interface LoadProduct {
    public Map<WebShopItem, Integer> loadData();
}
