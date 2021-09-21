package com.webshop.enums;

public enum PredefinedProducts { BEER("sör" , 500), BREAD("kenyér", 300)
    , MILK("tej", 250), YOGHURT("joghurt", 200)    ;


    public final String productValue;
    public final double price;

    private PredefinedProducts(String productValue, double price){
        this.productValue = productValue;
        this.price = price;
    }
}
