package com.webshop.dataloader;

import com.webshop.dataloader.interfaces.LoadUsers;
import com.webshop.webshop.Product;
import com.webshop.webshop.User;
import com.webshop.webshop.interfaces.WebShopItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class UserLoadFromFile implements LoadUsers {
    private String path;
    private static final String SEPARATOR = ";";

    public UserLoadFromFile(String path) {
        this.path = path;
    }

    @Override
    public Set<User> load() {
        Set<User> users = new HashSet();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] elements = line.split(SEPARATOR);
                if(!elements[0].equals("")) {
                    users.add(new User(Integer.parseInt(elements[0]), elements[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
