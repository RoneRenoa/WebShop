package com.webshop.dataloader;

import com.webshop.dataloader.interfaces.SaveUser;
import com.webshop.webshop.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UserSaveToFile implements SaveUser {
    private String path;
    private static final String SEPARATOR = ";";

    public UserSaveToFile(String path) {
        this.path = path;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
            bufferedWriter.write(user.getUserId() + SEPARATOR + user.getName());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
