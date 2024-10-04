package model;

public class Factory {
    public static ISistema getSistema() {
        return Sistema.getInstance();
    }
}