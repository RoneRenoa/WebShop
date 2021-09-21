package com.webshop.dataloader.interfaces;

import com.webshop.webshop.interfaces.WebShopItem;

import java.util.List;

public interface DataLoader {
    public List<WebShopItem> loadData();
}
