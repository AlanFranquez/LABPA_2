#!/bin/bash

echo "Iniciando script..."
cd svcentral

# Primero, compila el proyecto
mvn clean install

# Ejecuta la aplicación Swing en segundo plano (en el background)
java -cp target/svcentral-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.market.estacionDeTrabajo.Presentacion &

# Ejecuta el servidor también en segundo plano
java -jar target/svcentral-0.0.1-SNAPSHOT-jar-with-dependencies.jar
