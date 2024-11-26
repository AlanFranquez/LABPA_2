package com.market.svcentral;
import java.util.List;
import java.util.Map;

public class DTCliente {
    private String nombre, nick, apellido, correo, tipo;
    private DTFecha nacimiento;
    
    private List<OrdenDeCompra> ordenes;
	private String imagen;
	
	private boolean notificaciones;
    private String tokenDesactivacion; 
    
    public DTCliente(String nombre, String nick, String apellido, String correo, DTFecha nacimiento, String imagen, List<OrdenDeCompra> ordenes) {
        this.nombre = nombre;
        this.nick = nick;
        this.apellido = apellido;
        this.correo = correo;
        this.nacimiento = nacimiento;
        this.imagen = imagen;
        this.ordenes = ordenes;
        this.tipo = "Cliente";
    }
    // Gets
    
    // Métodos para el token de desactivación
    public String getTokenDesactivacion() {
        return tokenDesactivacion;
    }
    public void setTokenDesactivacion(String tokenDesactivacion) {
        this.tokenDesactivacion = tokenDesactivacion;
    }
  //notificaciones email
    public boolean isNotificaciones() {
        return notificaciones;
    }
    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }
    
    public String getNombre() {
        return nombre;
    }
    public String getNick() {
        return nick;
    }
    public String getApellido() {
        return apellido;
    }
    @Override
    public String toString() {
        return "Mail: " + correo + " | " + "Nick: " + nick; 
               
    }
    
    public String toStringCompleto() {
    	return 	
    			"Imagen no agregada todavía" +
    			System.lineSeparator() + System.lineSeparator() +
    			
    			"Mail: " + correo + System.lineSeparator() +
    			"Nick: " + nick + System.lineSeparator() +
    			"Nombre Completo: " + nombre + " " + apellido + System.lineSeparator() + 
    			"Fecha de nacimiento " + nacimiento.getDia() + " - " + nacimiento.getMes() +  " - " + nacimiento.getAnio();
    			
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
        System.out.println("Correo establecido: " + correo);
    }
    
    public DTCliente mostrarPerfil() {
		return this;
    	
    }
    public DTFecha getNacimiento() {
        return nacimiento;
    }
    
    public String getNacimientoFormateado() {
    	return this.nacimiento.getDia() + " / " + this.nacimiento.getMes() + " / " + this.nacimiento.getAnio();
    }
    public String getTipo() {
    	return tipo;
    }
    public String getImagenes() {
        return imagen;
    }
    public List<OrdenDeCompra> getOrdenes() {
        return ordenes;
    }
    
    public OrdenDeCompra getOrden(int num) {
    	return this.ordenes.get(num);
    }
	
}
