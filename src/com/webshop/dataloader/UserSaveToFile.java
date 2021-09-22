package com.webshop.dataloader;

import com.webshop.dataloader.interfaces.SaveUser;
import com.webshop.webshop.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class UserSaveToFile implements SaveUser {
    private String path;
    private static final String SEPARATOR = ";";
    public UserSaveToFile(String path) {
        this.path = path;
    }

    @Override
    public void save(User user) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
                bufferedWriter.append(user.getUserId() + SEPARATOR + user.getName());
                bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
