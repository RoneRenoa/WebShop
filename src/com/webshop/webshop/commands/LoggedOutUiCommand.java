package com.webshop.webshop.commands;

import com.webshop.enums.WelcomePageCommand;
import com.webshop.enums.interfaces.EnumCommand;
import com.webshop.webshop.interfaces.Command;

import java.util.Scanner;

public class LoggedOutUiCommand implements Command {
    private Scanner scanner;

    public LoggedOutUiCommand(Scanner scanner){
        this.scanner = scanner;
    }

    @Override
    public EnumCommand getCommand() {
        EnumCommand logoutMainPageCommand;
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                logoutMainPageCommand = WelcomePageCommand.REGISTRATION;
                break;
            case "2":
                logoutMainPageCommand = WelcomePageCommand.LOGIN;
                break;
            case "3":
                logoutMainPageCommand = WelcomePageCommand.EXIT;
                break;
            default:
                logoutMainPageCommand = WelcomePageCommand.INVALID;
        }
        return logoutMainPageCommand;
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }
}
