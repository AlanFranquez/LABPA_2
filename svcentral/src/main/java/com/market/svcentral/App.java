package com.market.svcentral;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        String nombre = "Usuario"; // Puedes cambiarlo o recibirlo como argumento
        saludar(nombre);
    }

    public static void saludar(String nombre) {
        System.out.println("Hola, " + nombre + "! Bienvenido a la aplicación.");
    }
}
