package com.webshop.webshop.commands;

import com.webshop.enums.LoggedMainPageCommand;
import com.webshop.enums.interfaces.EnumCommand;
import com.webshop.webshop.interfaces.Command;

import java.util.Scanner;

public class LoggedInUiCommand implements Command {
    private Scanner scanner;

    public LoggedInUiCommand(Scanner scanner){
        this.scanner = scanner;
    }

    @Override
    public EnumCommand getCommand() {
        
        EnumCommand loggedMainPageCommand;
        String command = scanner.nextLine();
        switch (command.toLowerCase()) {
            case "1":
                loggedMainPageCommand = LoggedMainPageCommand.PRODUCTS;
                break;
            case "2":
                loggedMainPageCommand = LoggedMainPageCommand.CART;
                break;
            case "3":
                loggedMainPageCommand = LoggedMainPageCommand.PAY;
                break;
            case "4":
                loggedMainPageCommand = LoggedMainPageCommand.HISTORY;
                break;
            case "5":
                loggedMainPageCommand = LoggedMainPageCommand.LOGOUT;
                break;
            default:
                loggedMainPageCommand = LoggedMainPageCommand.INVALID;
        }
        return loggedMainPageCommand;
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }
}
