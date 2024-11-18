package com.market.estacionDeTrabajo;

import java.awt.BorderLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.market.svcentral.*;
import com.market.svcentral.exceptions.*;

@SuppressWarnings("serial")
public class DetallesOrden extends JInternalFrame{
	private static ISistema s = Factory.getSistema();
	
	public DetallesOrden(DTOrdenDeCompra orden) {
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Detalles de Orden de Compra");
        setBounds(10, 40, 360, 150);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        
        
        panel.add(new JLabel("============================="));
        
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        DTOrdenDeCompra ordenEnDB = s.getOrden(orden.getNumero());
        
        if(ordenEnDB == null) {
        	JOptionPane.showMessageDialog(null, "Esta orden ya se ha eliminado");
        	return;
        } else {
        	List<DTItem> lista = ordenEnDB.listarItems();
        	for(DTItem l: lista) {
        		Producto p = l.getProducto();
        		DtProducto dtp = p.crearDT();
        		
        		panel.add(new JLabel("Nombre del producto: " + dtp.getNombre() + " - " + dtp.getPrecio()));
        		panel.add(new JLabel("Cantidad: " + l.getCant()));
        		panel.add(new JLabel("Subtotal: " + l.getSubTotal()));
        		panel.add(new JLabel("============================="));
        	}
        }
        
        panel.add(new JLabel("Precio total " + orden.getPrecioTotal()));
    
        JScrollPane scrollPane = new JScrollPane(panel);
        
        add(scrollPane, BorderLayout.CENTER);
        
        setVisible(true);
        setLocation(150, 150);
    }
}
