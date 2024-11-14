package com.market.estacionDeTrabajo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import com.market.svcentral.*;
import com.market.svcentral.exceptions.*;

@SuppressWarnings("serial")
public class EliminarOrden extends JInternalFrame {
    private static ISistema s = Factory.getSistema();
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");

    public EliminarOrden(DTOrdenDeCompra orden) {
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Eliminar Orden de Compra");
        setBounds(10, 40, 360, 150);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Crear EntityManager para la lectura inicial
        EntityManager em = emf.createEntityManager();
        OrdenDeCompra ordenDB = em.find(OrdenDeCompra.class, orden.getNumero());
        em.close();

        // Verificar si la orden existe antes de mostrar detalles
        if (ordenDB == null) {
            JOptionPane.showMessageDialog(null, "Esta orden ya se ha eliminado");
            dispose();
            return;
        }

        // Listar detalles de la orden
        List<DTItem> lista = ordenDB.crearDT().listarItems();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        panel.add(new JLabel("Numero de Orden: " + orden.getNumero()));
        panel.add(new JLabel("Fecha: " + orden.getFecha().format(formatter)));
        panel.add(new JLabel("============================="));

        for (DTItem l : lista) {
            Producto p = l.getProducto();
            DtProducto dtp = p.crearDT();
            panel.add(new JLabel("Producto: " + dtp.getNombre() + " - Precio: " + dtp.getPrecio()));
            panel.add(new JLabel("Cantidad: " + l.getCant()));
            panel.add(new JLabel("Subtotal: " + l.getSubTotal()));
            panel.add(new JLabel("============================="));
        }

        panel.add(new JLabel("Precio total: " + orden.getPrecioTotal()));
        JScrollPane scrollPane = new JScrollPane(panel);

        JButton eliminarButton = new JButton("Eliminar Orden");
        panel.add(eliminarButton);

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                int confirm = JOptionPane.showConfirmDialog(null, 
                        "¿Estás seguro de que deseas eliminar esta orden?", 
                        "Confirmación", 
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    EntityManager emEliminar = emf.createEntityManager();
                    try {
                    	emEliminar.getTransaction().begin();

                        // Buscar la orden de compra
                        OrdenDeCompra ordenEnDB = emEliminar.find(OrdenDeCompra.class, orden.getNumero());
                        
                        

                        
                        if (ordenEnDB != null) {
                            // Recorrer todos los clientes para eliminar la referencia a la orden
                            List<Cliente> clientes = emEliminar.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
                            
                            for (Cliente cliente : clientes) {
                            	
                            	List<OrdenDeCompra> ordenes = cliente.getOrdenes();
                                for(OrdenDeCompra ordSelecc: ordenes) {
                                	if(ordSelecc.getNumero() == ordenEnDB.getNumero()) {
                                		cliente.getOrdenes().remove(ordenEnDB);
                                		emEliminar.merge(cliente);
                                		break;
                                		
                                	}
                                }
                            }
                            
                            Query count = emEliminar.createNativeQuery(
                        		    "SELECT COUNT(*) FROM cliente_ordendecompra WHERE listacompras_numero = ?")
                        		    .setParameter(1, orden.getNumero());
                            
                            emEliminar.createNativeQuery(
                            	    "DELETE FROM cliente_ordendecompra WHERE listacompras_numero = ?")
                            	    .setParameter(1, orden.getNumero())
                            	    .executeUpdate();
                            
                            
                            emEliminar.remove(ordenEnDB);
                            emEliminar.getTransaction().commit();
                            JOptionPane.showMessageDialog(null, "Orden eliminada correctamente");
                        } else {
                            JOptionPane.showMessageDialog(null, "La orden ya no existe en la base de datos");
                        }

                        dispose();
                    } catch (Exception ex) {
                        if (emEliminar.getTransaction().isActive()) {
                            emEliminar.getTransaction().rollback();
                        }
                        JOptionPane.showMessageDialog(null, 
                                "Error al eliminar la orden: " + ex.getMessage(), 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                        
                        System.out.print(ex.getMessage());
                    } finally {
                        emEliminar.close();
                    }
                }
            }
        });

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
        setLocation(150, 150);
    }
}
