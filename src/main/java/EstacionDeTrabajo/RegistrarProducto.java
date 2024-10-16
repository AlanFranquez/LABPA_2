package EstacionDeTrabajo;

import com.model.*;
import com.exceptions.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class RegistrarProducto extends JInternalFrame {
    private static ISistema s = Factory.getSistema();
    private List<File> imagenesSeleccionadas = new ArrayList<>();

    public RegistrarProducto(DtProducto prod) {
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle(prod == null ? "Registrar Producto" : "Modificar Producto");
        setBounds(10, 40, 440, 600);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        initializeComponents(panel, prod);
        getContentPane().add(panel);
        setVisible(true);
        toFront();
    }

    private void initializeComponents(JPanel panel, DtProducto prod) {
        JLabel tituloLabel = new JLabel("Título:");
        tituloLabel.setBounds(20, 20, 80, 25);
        panel.add(tituloLabel);

        JTextField tituloField = new JTextField(20);
        tituloField.setBounds(100, 20, 200, 25);
        if (prod != null) tituloField.setText(prod.getNombre());
        panel.add(tituloField);

        JLabel referenciaLabel = new JLabel("Número de referencia:");
        referenciaLabel.setBounds(20, 50, 150, 25);
        panel.add(referenciaLabel);

        JTextField referenciaField = new JTextField(20);
        referenciaField.setBounds(185, 50, 200, 25);
        if (prod != null) referenciaField.setText(prod.getNumRef().toString());
        panel.add(referenciaField);

        JLabel stockLabel = new JLabel("Número de Stock:");
        stockLabel.setBounds(20, 80, 150, 25);
        panel.add(stockLabel);

        JTextField stockField = new JTextField(20);
        stockField.setBounds(185, 80, 200, 25);
        if (prod != null) stockField.setText(prod.getStock().toString());
        panel.add(stockField);

        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setBounds(20, 120, 100, 25);
        panel.add(descripcionLabel);

        JTextField descripcionField = new JTextField(20);
        descripcionField.setBounds(100, 120, 266, 25);
        if (prod != null) descripcionField.setText(prod.getDescripcion());
        panel.add(descripcionField);

        JLabel especificacionesLabel = new JLabel("Especificaciones:");
        especificacionesLabel.setBounds(20, 150, 150, 25);
        panel.add(especificacionesLabel);

        JTextArea especificacionesArea = new JTextArea();
        especificacionesArea.setBounds(20, 170, 394, 64);
        especificacionesArea.setLineWrap(true);
        if (prod != null) especificacionesArea.setText(prod.getEspecs());
        panel.add(especificacionesArea);

        JLabel precioLabel = new JLabel("Precio:");
        precioLabel.setBounds(20, 250, 80, 25);
        panel.add(precioLabel);

        JTextField precioField = new JTextField(10);
        precioField.setBounds(100, 250, 100, 25);
        if (prod != null) precioField.setText(String.valueOf((int) prod.getPrecio()));
        panel.add(precioField);

        JLabel proveedorLabel = new JLabel("Proveedor:");
        proveedorLabel.setBounds(20, 290, 100, 25);
        panel.add(proveedorLabel);

        JComboBox<String> padresCategorias = setupProveedorComboBox(panel, prod);
        panel.add(padresCategorias);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(20, 330, 80, 25);
        panel.add(lblCategoria);

        JTree tree = setupCategoriasTree(panel);
        panel.add(tree);

        setupImagenesSection(panel);

        JButton registrarButton = new JButton(prod == null ? "Crear" : "Guardar Cambios");
        registrarButton.setBounds(90, 500, 240, 25);
        panel.add(registrarButton);

        registrarButton.addActionListener(e -> handleRegistrarButton(prod, tituloField, referenciaField, stockField,
                                                                     descripcionField, especificacionesArea, precioField,
                                                                     padresCategorias, tree));
    }

    private JComboBox<String> setupProveedorComboBox(JPanel panel, DtProducto prod) {
        List<DTProveedor> proveedores = s.listarProveedores();
        String[] nombres = proveedores.stream().map(DTProveedor::getNick).toArray(String[]::new);

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(nombres);
        JComboBox<String> padresCategorias = new JComboBox<>(comboBoxModel);
        padresCategorias.setBounds(100, 290, 160, 25);
        padresCategorias.setEnabled(prod == null);

        if (prod != null) {
            JLabel proveedor = new JLabel(prod.getNicknameProveedor());
            proveedor.setBounds(100, 290, 160, 25);
            panel.add(proveedor);
        }

        return padresCategorias;
    }

    private JTree setupCategoriasTree(JPanel panel) {
        DefaultMutableTreeNode root = s.arbolCategorias();
        JTree tree = new JTree(root);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setCellRenderer(new DefaultTreeCellRenderer());
        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setBounds(80, 350, 266, 90);
        panel.add(scrollPane);
        return tree;
    }

    private void setupImagenesSection(JPanel panel) {
        JLabel imagenesLabel = new JLabel("Imágenes:");
        imagenesLabel.setBounds(20, 450, 100, 25);
        panel.add(imagenesLabel);

        JButton seleccionarImagenButton = new JButton("Seleccionar Imágenes");
        seleccionarImagenButton.setBounds(100, 450, 200, 25);
        panel.add(seleccionarImagenButton);

        JLabel imagenesSeleccionadasLabel = new JLabel("No se ha seleccionado ninguna imagen");
        imagenesSeleccionadasLabel.setBounds(100, 470, 300, 25);
        panel.add(imagenesSeleccionadasLabel);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        seleccionarImagenButton.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File[] archivosSeleccionados = fileChooser.getSelectedFiles();
                imagenesSeleccionadas.clear();
                StringBuilder imagenesNombres = new StringBuilder();

                for (File archivo : archivosSeleccionados) {
                    String nombreArchivo = archivo.getName().toLowerCase();
                    if (nombreArchivo.endsWith(".jpg") || nombreArchivo.endsWith(".png") || nombreArchivo.endsWith(".jpeg")) {
                        imagenesSeleccionadas.add(archivo);
                        imagenesNombres.append(archivo.getName()).append("; ");
                    } else {
                        imagenesSeleccionadas.clear();
                        JOptionPane.showMessageDialog(null, "El archivo " + archivo.getName() + " no es válido. Seleccione archivos .jpg o .png", "Archivo no válido", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                //imagenesSeleccionadasLabel.setText(imagenasSeleccionadas.isEmpty() ? "No se ha seleccionado ninguna imagen" : "Imágenes seleccionadas: " + imagenesNombres);
           }
        });
    }

    private void handleRegistrarButton(DtProducto prod, JTextField tituloField, JTextField referenciaField,
                                       JTextField stockField, JTextField descripcionField,
                                       JTextArea especificacionesArea, JTextField precioField,
                                       JComboBox<String> padresCategorias, JTree tree) {
        String proveedor = (String) padresCategorias.getSelectedItem();
        if (prod != null) {
            proveedor = null;  // Si estamos editando, no se necesita proveedor.
        }

        String titulo = tituloField.getText();
        String descripcion = descripcionField.getText();
        String especificaciones = especificacionesArea.getText();
        String precioStr = precioField.getText();

        if (titulo.isEmpty() || referenciaField.getText().isEmpty() || descripcion.isEmpty() || especificaciones.isEmpty() || precioStr.isEmpty() || (prod == null && proveedor.isEmpty())) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int precio;
        try {
            precio = Integer.parseInt(precioStr);
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "El precio debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numRef;
        try {
            numRef = Integer.parseInt(referenciaField.getText());
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "El número de referencia no puede ser un string", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int stock;
        try {
            stock = Integer.parseInt(stockField.getText());
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "El stock debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TreePath selectedPath = tree.getSelectionPath();
        if (selectedPath == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una categoría.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> categoriasSeleccionadas = new ArrayList<>();
        for (TreePath path : tree.getSelectionPaths()) {
            categoriasSeleccionadas.add(path.getLastPathComponent().toString());
        }

        try {
            if (prod == null) {
            	s.agregarProducto(titulo, numRef, descripcion, especificaciones, precio, proveedor, precio);
                JOptionPane.showMessageDialog(null, "Producto creado con éxito");
            } 
            else {
              /* s.modificarProducto(new DtProducto(titulo, numRef, stock, 
            //descripcion, especificaciones, precio, prod.getNicknameProveedor(), /categoriasSeleccionadas, imagenesSeleccionadas));*/
                JOptionPane.showMessageDialog(null, "Producto modificado con éxito");
            }
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
