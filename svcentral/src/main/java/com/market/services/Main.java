package com.market.services;

public class Main {
    public static void main(String[] args) {
        try {
            Publicador publicador = new Publicador();
            publicador.publicar("http://localhost:1234/publicador");
            
            String saludo = publicador.saludar();
            
            System.out.print(saludo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
