package estacionDeTrabajo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.market.svcentral.*;
import com.market.svcentral.exceptions.*;

@SuppressWarnings("serial")
public class CrearOrdenCompra extends JInternalFrame {
    private static ISistema s = Factory.getSistema();

    public CrearOrdenCompra() {
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Crear Orden de Compra");
        setBounds(10, 40, 412, 400);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        getContentPane().add(panel);

        JLabel tituloLabel = new JLabel("Cliente:");
        tituloLabel.setBounds(20, 20, 80, 25);
        panel.add(tituloLabel);

        // CLIENTES
        List<DTCliente> clientes = s.listarClientes();
        String[] nombres = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            nombres[i] = clientes.get(i).getNick();
        }

       
        JComboBox<String> comboBoxClientes = new JComboBox<>(nombres);
        comboBoxClientes.setBounds(73, 20, 160, 25);
        panel.add(comboBoxClientes);

        
        JLabel lblCategoria = new JLabel("Producto:");
        lblCategoria.setBounds(20, 56, 80, 25);
        panel.add(lblCategoria);

        DefaultMutableTreeNode root = s.arbolProductos();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLocation(73, 56);
        scrollPane.setSize(266, 158);
        panel.add(scrollPane);

        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        JTree tree = new JTree(root);
        scrollPane.setViewportView(tree);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setCellRenderer(renderer);
        tree.clearSelection();

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(20, 225, 80, 25);
        panel.add(lblCantidad);

        JTextField cantidadField = new JTextField();
        cantidadField.setBounds(82, 225, 96, 20);
        panel.add(cantidadField);

        JButton productoButton = new JButton("Agregar Producto");
        productoButton.setBounds(224, 231, 134, 23);
        panel.add(productoButton);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Número de Referencia");
        model.addColumn("Cantidad");
        model.addRow(new Object[]{"Número de Referencia", "Cantidad"});
        JTable lista = new JTable(model);
        lista.setBounds(52, 261, 287, 62);
        panel.add(lista);

        JButton registrarButton = new JButton("Crear");
        registrarButton.setBounds(73, 334, 240, 25);
        panel.add(registrarButton);

        productoButton.addActionListener(e -> {
            TreePath[] productos = tree.getSelectionPaths();
            if (productos == null) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadField.getText());
                if (cantidad <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Cantidad debe ser un número entero mayor que 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (TreePath path : productos) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (!selectedNode.isLeaf()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String selection = (String) selectedNode.getUserObject();
                String[] parts = selection.split(" - ");
                int numRef;

                try {
                    numRef = Integer.parseInt(parts[1].split(" ")[0]); 
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error al obtener el número de referencia.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (s.obtenerStockProducto(numRef) < cantidad) {
                    JOptionPane.showMessageDialog(null, "La solicitud excede el stock disponible", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                model.addRow(new Object[]{numRef, cantidad});
                cantidadField.setText("");
            }
        });

        registrarButton.addActionListener(e -> {
            String cliente = (String) comboBoxClientes.getSelectedItem();
            if (cliente == null || cliente.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<Integer, Item> itemsAgregados = new HashMap<>();
            for (int i = 1; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0) == null || model.getValueAt(i, 1) == null) {
                    JOptionPane.showMessageDialog(null, "El número de referencia y la cantidad no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int numRef;
                int cant;

                try {
                    numRef = Integer.parseInt(model.getValueAt(i, 0).toString());
                    cant = Integer.parseInt(model.getValueAt(i, 1).toString());
                    if (numRef <= 0 || cant <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El número de referencia y la cantidad deben ser números enteros válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Producto producto = s.getProducto(numRef);
                if (producto != null) {
                    itemsAgregados.put(numRef, new Item(cant, producto));
                }
            }

            float precioTotal = (float) itemsAgregados.values().stream().mapToDouble(Item::getSubTotal).sum();
            try {
                OrdenDeCompra orden = new OrdenDeCompra(itemsAgregados, precioTotal);
                s.realizarCompra(orden, cliente);
                model.setRowCount(1); // Reiniciar la tabla dejando el encabezado
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al realizar la compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
        toFront();
    }
}
