package com.market.svcentral;

import services.Publicador;

import javax.xml.ws.Endpoint;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class Main {
    private static final Properties config = new Properties();
    private static Endpoint endpoint = null;

    public static void main(String[] args) {
        limpiarConsola();
        mostrarBanner();
        
        if (cargarConfiguracion()) {
            System.out.println("Configuración cargada correctamente.");
            iniciarServidor();
            esperarEntradaParaDetenerServidor();
        } else {
            System.err.println("Error: No se pudo cargar la configuración. Saliendo...");
        }
    }

    private static boolean cargarConfiguracion() {
        String rutaCarpeta;

        // Detectar sistema operativo para definir la ruta
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            rutaCarpeta = "C:/Users/Usuario/DirectMarket"; // Windows
        } else {
            String userHome = System.getProperty("user.home"); // Linux/Mac
            rutaCarpeta = userHome + "/DirectMarket";
        }

        String rutaConfig = rutaCarpeta + "/config.properties";
        File carpeta = new File(rutaCarpeta);

        if (!carpeta.exists() && !carpeta.mkdirs()) {
            System.err.println("No se pudo crear la carpeta de configuración: " + rutaCarpeta);
            return false;
        }

        File archivoConfig = new File(rutaConfig);
        if (!archivoConfig.exists()) {
            crearArchivoConfig(archivoConfig);
        }

        try (FileInputStream configFis = new FileInputStream(rutaConfig)) {
            config.load(configFis);
            return true;
        } catch (IOException e) {
            //System.err.println("Error al cargar el archivo config.properties: " + e.getMessage());
            return false;
        }
    }

   private static void crearArchivoConfig(File archivoConfig) {
        try (FileWriter writer = new FileWriter(archivoConfig)) {
            String ipServidor = obtenerIPLocal(); // Obtener la IP dinámica

            writer.write("# Configuración del servidor\n");
            writer.write("webservice.url=http://" + ipServidor + ":1234/publicador\n"); // Usar la IP dinámica
            writer.write("servidor.central.puerto=1234\n");
            System.out.println("Archivo config.properties creado en: " + archivoConfig.getPath());
        } catch (IOException e) {
            //System.err.println("Error al crear el archivo config.properties: " + e.getMessage());
        }
    }


    private static void iniciarServidor() {
        try {
            String ipServidor = obtenerIPLocal();
            String puertoServidor = config.getProperty("servidor.central.puerto", "1234");
            String urlServidor = config.getProperty("webservice.url", "http://" + ipServidor + ":" + puertoServidor + "/publicador");
            //String urlServidor = config.getProperty("webservice.url", "http://192.168.1.3:1234/publicador");

            Publicador publicador = new Publicador();
            endpoint = Endpoint.publish(urlServidor, publicador);

            System.out.println("Servidor iniciado y publicado en: " + urlServidor);
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private static String obtenerIPLocal() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Error al obtener la dirección IP local: " + e.getMessage());
            return "localhost";
        }
    }

    private static void esperarEntradaParaDetenerServidor() {
        System.out.println("Presiona ENTER para detener el servidor...");
        try {
            System.in.read();
            detenerServidor();
        } catch (IOException e) {
            System.err.println("Error al esperar entrada para detener el servidor: " + e.getMessage());
        }
    }

    private static void detenerServidor() {
        if (endpoint != null && endpoint.isPublished()) {
            endpoint.stop();
            System.out.println("Servidor detenido correctamente.");
        } else {
            System.out.println("El servidor ya estaba detenido o no se inició correctamente.");
        }
    }

    private static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.err.println("No se pudo limpiar la consola: " + e.getMessage());
        }
    }

    private static void mostrarBanner() {
        System.out.println("  ____  _               _   __  __            _        _   ");
        System.out.println(" |  _ \\(_)_ __ ___  ___| |_|  \\/  | __ _ _ __| | _____| |_ ");
        System.out.println(" | | | | | '__/ _ \\/ __| __| |\\/| |/ _` | '__| |/ / _ \\ __|");
        System.out.println(" | |_| | | | |  __/ (__| |_| |  | | (_| | |  |   <  __/ |_ ");
        System.out.println(" |____/|_|_|  \\___|\\___|\\__|_|  |_|\\__,_|_|  |_|\\_\\___|\\__|");
        System.out.println();
    }
}
