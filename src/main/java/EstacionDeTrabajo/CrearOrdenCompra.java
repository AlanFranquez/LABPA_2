package EstacionDeTrabajo;

import com.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class CrearOrdenCompra extends JInternalFrame {
    private static ISistema s = Factory.getSistema();
    private Map<Integer, Item> itemsOrdenCompra;
    private float precioTotal;

    public CrearOrdenCompra() {
        itemsOrdenCompra = new HashMap<>(); // Inicializar la lista de items
        precioTotal = 0; // Inicializar el precio total

        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Crear Orden de Compra");
        setBounds(10, 40, 360, 150);
        setSize(412, 400);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel tituloLabel = new JLabel("Cliente:");
        tituloLabel.setBounds(20, 20, 80, 25);
        panel.add(tituloLabel);
        
        List<DTCliente> clientes = s.listarClientes();
        String[] nombres = clientes.stream().map(DTCliente::getNick).toArray(String[]::new);
        
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(nombres);
        JComboBox<String> comboBoxClientes = new JComboBox<>(comboBoxModel);
        comboBoxClientes.setBounds(73, 20, 160, 25);
        panel.add(comboBoxClientes); 
        
        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(20, 56, 80, 25);
        panel.add(lblProducto);
        
        DefaultMutableTreeNode root = s.arbolProductos();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLocation(73, 56);
        scrollPane.setSize(266, 158);
        panel.add(scrollPane);
        
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            Icon closedIcon = s.resizeIcon(new ImageIcon("./imagenes/sinElementos.png"), 16, 16);

            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                          boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if ("Sin Elementos".equals(value.toString())) {
                    setIcon(closedIcon);
                }
                return this;
            }
        };
        
        JTree tree = new JTree(root);
        scrollPane.setViewportView(tree);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setCellRenderer(renderer);
        
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
        JTable lista = new JTable(model);
        lista.setBounds(52, 261, 287, 62);
        panel.add(new JScrollPane(lista)); // Usar JScrollPane para la tabla

        JButton registrarButton = new JButton("Crear");
        registrarButton.setBounds(73, 334, 240, 25);
        panel.add(registrarButton);

        productoButton.addActionListener(b -> {
            TreePath[] productos = tree.getSelectionPaths();
            if (productos == null || productos.length == 0) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int cant;
            try {
                cant = Integer.parseInt(cantidadField.getText());
                if (cant <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cantidad debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
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
                int numRef = Integer.parseInt(parts[1]);

                if (s.obtenerStockProducto(numRef) < cant) {
                    JOptionPane.showMessageDialog(null, "La solicitud excede el stock disponible", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Agregar el item a la lista
                Item item = new Item(cant, s.getProducto(numRef));
                itemsOrdenCompra.put(numRef, item);
                float precioProducto = s.getProducto(numRef).getPrecio(); // Obtener el precio del producto
                precioTotal += precioProducto * cant; // Calcular el precio total
                
                model.addRow(new Object[]{numRef, cant});
                cantidadField.setText("");
            }
        });
        
        registrarButton.addActionListener(b -> {
            String cliente = (String) comboBoxModel.getSelectedItem();
            if (cliente == null || cliente.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mostrar el precio total
            JOptionPane.showMessageDialog(null, "Precio Total: " + precioTotal, "Total", JOptionPane.INFORMATION_MESSAGE);

            // Crear la orden de compra y realizar la compra
            OrdenDeCompra orden = new OrdenDeCompra(itemsOrdenCompra, precioTotal);
            s.realizarCompra(orden, cliente); // Método para realizar la compra

            model.setRowCount(0); // Limpiar la tabla
            model.addRow(new Object[]{"Número de Referencia", "Cantidad"}); // Reiniciar encabezados
            itemsOrdenCompra.clear(); // Limpiar la lista de items
            precioTotal = 0; // Reiniciar el precio total
        });

        getContentPane().add(panel);
        setVisible(true);
        toFront();
    }
}
