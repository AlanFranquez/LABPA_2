package com.market.svcentral;

public class Factory {
    public static ISistema getSistema() {
        return Sistema.getInstance();
    }
}