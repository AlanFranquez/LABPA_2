package com.market.estacionDeTrabajo;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.market.svcentral.*;
import com.market.svcentral.exceptions.*;

@SuppressWarnings("serial")
public class RegistrarProducto extends JInternalFrame{
	private static ISistema s = Factory.getSistema();
	
	private File imagenSeleccionada = null;
	private List<File> imagenesSeleccionadas = new ArrayList<File>();
	
	public RegistrarProducto(DtProducto prod) {
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        
        if(prod == null)
        	setTitle("Registrar Producto");
        else
        	setTitle("Modificar Producto");
        setBounds(10, 40, 360, 150);
        setSize(440, 600);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        
        JLabel tituloLabel = new JLabel("Título:");
        tituloLabel.setBounds(20, 20, 80, 25);
        panel.add(tituloLabel);

        JTextField tituloField = new JTextField(20);
        tituloField.setBounds(100, 20, 200, 25);
        if(prod != null)
        	tituloField.setText(prod.getNombre());
        panel.add(tituloField);

        JLabel referenciaLabel = new JLabel("Número de referencia:");
        referenciaLabel.setBounds(20, 50, 150, 25);
        panel.add(referenciaLabel);        
        
        JTextField referenciaField = new JTextField(20);
        referenciaField.setBounds(185, 50, 200, 25);
        if(prod != null)
        	referenciaField.setText(prod.getNumRef().toString());
        panel.add(referenciaField);
        
        JLabel stockLabel = new JLabel("Número de Stock:");
        stockLabel.setBounds(20, 80, 150, 25);
        panel.add(stockLabel);

        
        JTextField stockField = new JTextField(20);
        stockField.setBounds(185, 80, 200, 25);
        if(prod != null)
        	stockField.setText(prod.getStock().toString());
        panel.add(stockField);

        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setBounds(20, 120, 100, 25);
        panel.add(descripcionLabel);

        JTextField descripcionField = new JTextField(20);
        descripcionField.setBounds(100, 120, 266, 25);
        if(prod != null)
        	descripcionField.setText(prod.getDescripcion());
        panel.add(descripcionField);

        JLabel especificacionesLabel = new JLabel("Especificaciones:");
        especificacionesLabel.setBounds(20, 150, 150, 25);
        panel.add(especificacionesLabel);

        JTextArea especificacionesArea = new JTextArea();
        especificacionesArea.setBounds(20, 170, 394, 64);
        especificacionesArea.setLineWrap(true);
        if(prod != null)
        	especificacionesArea.setText(prod.getEspecs());
        panel.add(especificacionesArea);
        

        JLabel precioLabel = new JLabel("Precio:");
        precioLabel.setBounds(20, 250, 80, 25);
        panel.add(precioLabel);

        JTextField precioField = new JTextField(10);
        precioField.setBounds(100, 250, 100, 25);
        if(prod != null)
        	precioField.setText(String.valueOf((int)prod.getPrecio()));
        panel.add(precioField);
        
        JLabel proveedorLabel = new JLabel("Proveedor:");
        proveedorLabel.setBounds(20, 290, 100, 25);
        panel.add(proveedorLabel);
        
        List<DTProveedor> proveedores = s.listarProveedores();
        String[] nombres;
        nombres = new String[proveedores.size()];
        for (int i = 0; i < proveedores.size(); i++) {
        	DTProveedor prov = proveedores.get(i);
            nombres[i] = prov.getNick();
        }
        
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(nombres);
        JComboBox<String> padresCategorias = new JComboBox<>(comboBoxModel);
        padresCategorias.setBounds(100, 290, 160, 25);
        padresCategorias.setEnabled(true);
        panel.add(padresCategorias);
        
        if(prod != null) {
        	padresCategorias.setVisible(false);
        	JLabel proveedor = new JLabel(prod.getNicknameProveedor());
        	proveedor.setBounds(100, 290, 160, 25);
            panel.add(proveedor);
        }
        
        
        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(20, 330, 80, 25);
        panel.add(lblCategoria);
        
        DefaultMutableTreeNode root = s.arbolCategorias();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLocation(80, 350);

        scrollPane.setSize(266, 90);
        scrollPane.setVisible(true);
        panel.add(scrollPane);
        
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            // Íconos personalizados
        	Icon closedIcon = s.resizeIcon(new ImageIcon("./imagenes/sinElementos.png"), 16, 16);

            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                          boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                // Modificar el ícono según el tipo de nodo
                if(value.toString() == "Sin Elementos") {
                	setIcon(closedIcon);
                }

                return this;
            }
        };
        JTree tree = new JTree(root);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setCellRenderer(renderer);
        scrollPane.setViewportView(tree);
        tree.clearSelection();
        
        JLabel imagenLabel = new JLabel("No se ha seleccionado ninguna imagen");
        imagenLabel.setBounds(20, 500, 240, 25);
        panel.add(imagenLabel);

        JButton seleccionarImagenButton = new JButton("Seleccionar Imágenes");
        seleccionarImagenButton.setBounds(100, 450, 200, 25);
        panel.add(seleccionarImagenButton);

        JLabel imagenesSeleccionadasLabel = new JLabel("No se ha seleccionado ninguna imagen");
        imagenesSeleccionadasLabel.setBounds(100, 470, 300, 25);
        panel.add(imagenesSeleccionadasLabel);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
       
        
        
        List<byte[]> imagenesBytes = new ArrayList<>(); // Lista para almacenar bytes de imágenes

     // Acción del botón de seleccionar imágenes
     seleccionarImagenButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e1) {
             int returnValue = fileChooser.showOpenDialog(null);
             if (returnValue == JFileChooser.APPROVE_OPTION) {
                 File[] imagenesSeleccionadasArray = fileChooser.getSelectedFiles();
                 imagenesSeleccionadas.clear();
                 imagenesBytes.clear();
                 
                 StringBuilder nombresArchivos = new StringBuilder("Seleccionadas: ");
                 
                 for (File imagen : imagenesSeleccionadasArray) {
                     if (imagen != null && (imagen.getName().endsWith(".jpg") || imagen.getName().endsWith(".png"))) {
                         imagenesSeleccionadas.add(imagen); 
                         nombresArchivos.append(imagen.getName()).append(", ");
                         
                         try {
                             byte[] bytes = Files.readAllBytes(imagen.toPath());
                             imagenesBytes.add(bytes);
                         } catch (IOException ex) {
                             JOptionPane.showMessageDialog(null, "Error al leer el archivo " + imagen.getName(), "Error", JOptionPane.ERROR_MESSAGE);
                         }
                     } else {
                         JOptionPane.showMessageDialog(null, "Archivo no válido: " + imagen.getName(), "Error", JOptionPane.ERROR_MESSAGE);
                     }
                 }
                 
                 if (!imagenesSeleccionadas.isEmpty()) {
                     imagenesSeleccionadasLabel.setText(nombresArchivos.toString()); // Mostrar nombres de imágenes seleccionadas
                 } else {
                     imagenesSeleccionadasLabel.setText("No se ha seleccionado ninguna imagen válida");
                 }
             }
         }
     });
        
        
        JButton registrarButton = new JButton("Crear");
        if(prod != null)
        	registrarButton.setText("Guardar Cambios");
        registrarButton.setBounds(90, 500, 240, 25);
        panel.add(registrarButton);
        
        getContentPane().add(panel);   
        
        // Validar y registrar el producto en el sistema
        registrarButton.addActionListener(b -> {
        	String proveedor = (String) comboBoxModel.getSelectedItem();
        	if(prod != null) {
        		proveedor = prod.getNicknameProveedor();
        	}
            String titulo = tituloField.getText();
            String descripcion = descripcionField.getText();
            String especificaciones = especificacionesArea.getText();
            String precioStr = precioField.getText();
            
            if(prod == null) {
            	if (titulo.isEmpty() || referenciaField.getText().isEmpty() || descripcion.isEmpty() || especificaciones.isEmpty() || precioStr.isEmpty() || proveedor.isEmpty()) {
                	JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                	return;
                }
            }
            else {
            	if (titulo.isEmpty() || referenciaField.getText().isEmpty() || descripcion.isEmpty() || especificaciones.isEmpty() || precioStr.isEmpty()) {
                	JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                	return;
                }
            }
            
            int precio;
            
            try {
            	precio = Integer.parseInt(precioStr);
            	
            } catch(NumberFormatException e1) {
            	JOptionPane.showMessageDialog(null, "El precio debe ser un numero", "Error", JOptionPane.ERROR_MESSAGE);
            	return;
            }
            
            int numRef;
            try {
            	numRef = Integer.parseInt(referenciaField.getText());
            	
            } catch(NumberFormatException e1) {
            	JOptionPane.showMessageDialog(null, "El numero de referencia no puede ser un string", "Error", JOptionPane.ERROR_MESSAGE);
            	return;
            }
            
            int Stock;
            try {
            	Stock = Integer.parseInt(stockField.getText());
            	
            } catch(NumberFormatException e1) {
            	JOptionPane.showMessageDialog(null, "El stock no puede ser un string", "Error", JOptionPane.ERROR_MESSAGE);
            	return;
            }
            
            TreePath[] selectedPaths = tree.getSelectionPaths();
            if(selectedPaths == null) {
            	JOptionPane.showMessageDialog(null, "Recuerde ingresar una Categoría", "Error", JOptionPane.ERROR_MESSAGE);
            	return;
            }
            
            for (TreePath path : selectedPaths) {
            	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            	String catName = selectedNode.getUserObject().toString();
            	try {
					s.comprobarCat(catName);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Todas las categorias deben ser válidas");
					return;
				}	
            }
            if(prod != null) {
            	s.borrarProducto(prod.getNumRef(), prod.getNombre());
            }
            if(!s.verificarUnicidadProducto(numRef, titulo)) {
            	JOptionPane.showMessageDialog(null, "El nombre o el numero de referencia ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            	return;
            }
            
            s.agregarProducto(titulo, numRef, descripcion,especificaciones, precio, proveedor, Stock);
            
            for (TreePath path : selectedPaths) {
            	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            	String catName = selectedNode.getUserObject().toString();            	
            	
                if(s.esPadre(catName)) {
                	JOptionPane.showMessageDialog(null, "Alguna de las categorias seleccionadas no es una categoria válida", "Error", JOptionPane.ERROR_MESSAGE);
                	
                	s.borrarProducto(prod.getNumRef(), prod.getNombre());
                	return;
                }
                try {
                	s.agregarProductoCategoria(catName, numRef);
                } catch(CategoriaException e1) {
            		s.borrarProducto(prod.getNumRef(), prod.getNombre());
            		JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            }
            for(File img: imagenesSeleccionadas) {
            	
            	byte[] imgByte = null;
				try {
					imgByte = Files.readAllBytes(img.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	s.agregarImagenProductoBytes(numRef, imgByte);
            }
                
            if(prod == null) {
            	JOptionPane.showMessageDialog(null, "Producto registrado con éxito.");
            } else {
            	JOptionPane.showMessageDialog(null, "Producto modificado con éxito.");
            }
            
            tituloField.setText("");
            referenciaField.setText("");
            descripcionField.setText("");
            especificacionesArea.setText("");
            precioField.setText("");
            comboBoxModel.setSelectedItem(nombres[0]);
            tree.clearSelection();
            imagenesSeleccionadasLabel.setText("No se ha seleccionado ninguna imagen");
            setVisible(false);
            
        });
        
        getContentPane().add(panel);
        setVisible(true);
        toFront();
        
	}
	
}
