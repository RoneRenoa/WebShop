package com.webshop.enums;

import com.webshop.enums.interfaces.EnumCommand;

public enum LoggedMainPageCommand implements EnumCommand {
    CART,
    PRODUCTS,
    PAY,
    HISTORY,
    LOGOUT,
    INVALID
}
