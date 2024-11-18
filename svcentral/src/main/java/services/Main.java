package services;

import com.market.svcentral.Cliente;
import com.market.svcentral.OrdenDeCompra;

public class Main {
	public static void main(String[] args) {
		Publicador p = new Publicador();
		
		p.publicar();
		
		System.out.print(p.saludar());
		Cliente cl = p.obtenerCliente("Juan123");
		
		
	}
}