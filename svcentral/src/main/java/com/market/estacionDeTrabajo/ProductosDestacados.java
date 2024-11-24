package com.market.estacionDeTrabajo;
import javax.swing.*;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductosDestacados extends JInternalFrame {
    private static ISistema s = Factory.getSistema();

    public ProductosDestacados() {
        setTitle("Productos Destacados");
        setSize(600, 400); // Aumenté el tamaño para adaptarse al JTable
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        List<Producto> productos = s.obtenerProductosDestacados();

        String[] columnNames = {"Nombre", "Proveedor", "Compras"};

        Object[][] data = new Object[productos.size()][3];

       
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            data[i][0] = producto.getNombre();      
            data[i][1] = producto.getProveedor().getNick(); 
            data[i][2] = producto.getComprasUnicas();   
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(tableModel);

        
        JScrollPane scrollPane = new JScrollPane(table);

      
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

   
        getContentPane().add(panel);
    }
}
