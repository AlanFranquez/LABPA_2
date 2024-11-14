package com.market.estacionDeTrabajo;

import java.awt.BorderLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.market.svcentral.*;
import com.market.svcentral.exceptions.*;

@SuppressWarnings("serial")
public class ListarOrdenes extends JInternalFrame{
	private static ISistema s = Factory.getSistema();
	
	public ListarOrdenes(String tipo) {
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle( tipo + " Ordenes de Compra");
        setBounds(10, 40, 360, 150);
        setSize(500, 300);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        
        
        em.getTransaction().begin();
	     List<OrdenDeCompra> ordenes = em.createQuery("SELECT c FROM OrdenDeCompra c").getResultList();
        
        if(ordenes == null || ordenes.isEmpty()) {
        	JOptionPane.showMessageDialog(null, "Todavia no hay ordenes para listar");
        	return;
        }
	       
     
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
   	 
	     // Definir las columnas de la tabla
	     String[] columnNames = {"Numero de Orden", "Fecha"};

	     // Crear datos para la tabla
	     Object[][] data = new Object[ordenes.size()][3];
	     for (int i = 0; i < ordenes.size(); i++) {
	    	 DTOrdenDeCompra o = ordenes.get(i).crearDT();
	    	 data[i][0] = o.getNumero();
	    	 data[i][1] = o.getFecha().format(formatter).toString();
	     }

	     // Crear la tabla
	     JTable table = new JTable(data, columnNames);
	     table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     
	     // Agregar un listener para manejar clics en la tabla
	     table.addMouseListener(new java.awt.event.MouseAdapter() {
	    	 @Override
	    	 public void mouseClicked(java.awt.event.MouseEvent evt) {
	    		 int row = table.rowAtPoint(evt.getPoint());
	    		 if (row >= 0) {
	    			 DTOrdenDeCompra o = ordenes.get(row).crearDT();
	    			 if (tipo == "Detalles") {
	    				 DetallesOrden ord = new DetallesOrden(o);
	    				 getParent().add(ord);
	    				 try {
	    			            ord.setSelected(true);
	    			            ord.toFront();
	    			        } catch (java.beans.PropertyVetoException e) {
	    			            e.printStackTrace();
	    			        }
	    			 }else if(tipo == "Eliminar") {
	    				 EliminarOrden ord = new EliminarOrden(o);
	    				 getParent().add(ord);
	    				 dispose();
	    				 try {
	    			            ord.setSelected(true);
	    			            ord.toFront();
	    			        } catch (java.beans.PropertyVetoException e) {
	    			            e.printStackTrace();
	    			        }
	    			 }
	    		 }
	    		
	    	 }
	    	 
	    	
	     });

	     JScrollPane scrollPane = new JScrollPane(table);
	     getContentPane().add(scrollPane, BorderLayout.CENTER);
        
	     setVisible(true);
	     toFront();
	     
	     em.getTransaction().commit();
		 em.close();
		 emf.close();
	}
}
