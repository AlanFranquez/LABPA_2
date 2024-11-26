package com.market.svcentral;

import services.Publicador;

public class Main {
	public static void main(String[] args) {
		Publicador p = new Publicador();
		
		p.publicar();
		
		System.out.print(p.saludar());
		Cliente cl = p.obtenerCliente("Juan123");
		
		
	}
}