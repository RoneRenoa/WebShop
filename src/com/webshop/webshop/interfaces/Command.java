package com.webshop.webshop.interfaces;

import com.webshop.enums.interfaces.EnumCommand;

public interface Command {
    EnumCommand getCommand();
    String getInput();
}
