package com.market.estacionDeTrabajo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.toedter.calendar.JDateChooser;

import com.market.svcentral.*;
import com.market.svcentral.exceptions.*;

public class Presentacion {

    private JFrame frame;
    private File imagenSeleccionada;
    private String imagenSelecc;
    private JDesktopPane desktopPane;
    private static ISistema s = Factory.getSistema();
    private JFileChooser fileChooser;
    Calendar calendar = Calendar.getInstance();
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    private EntityManager em = emf.createEntityManager();
    
    /**
     * Launch the application.
     */
    
 
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
  
                try {
                    DTFecha fecha1 = new DTFecha(1, 1, 1990);
                    DTFecha fecha2 = new DTFecha(15, 6, 1985);
                    DTFecha fecha3 = new DTFecha(5, 6, 1990);

                    s.agregarCliente("Juan", "Juan123", "Perez", "Juan@gmail.com", fecha1, "123", "123");
                    s.agregarCliente("Alberto", "albert1341", "Hernandez", "Ahernandez@gmail.com", fecha2, "123", "123");
                    s.agregarCliente("Maria", "agusmari", "Agustina", "mariaagustina@gmail.com", fecha1, "123", "123");

                    //s.agregarImagenUsuario("Juan123", new ImageIcon("./imagenes/p1.jpg"));
                    //s.agregarImagenUsuario("albert1341", new ImageIcon("./imagenes/p2.jpg"));
                    //s.agregarImagenUsuario("agusmari", new ImageIcon("./imagenes/p3.jpg"));
                    
                    try {
                    	s.agregarCategoria("Tecnología");
                    	s.agregarCategoria("Estanterias");
                    	
                    	s.agregarCategoriaConProductos("Tecno");
                    	s.agregarCategoriaConProductos("Otros");
                    	s.agregarCategoriaConProductos("Bazar");                    	
                    } catch(Exception e) {
                    	System.out.println(e.getMessage());
                    }

                    
                    
                   
                    Proveedor prov = new Proveedor("Perez", "AndresPerez@gmail.com", "Andres", "Perez", fecha3 ,"Bamboo.inc" , "www.bamboo.com", "123");
                    s.agregarProveedor("Perez", "AndresPerez@gmail.com", "Andres", "Perez", fecha3 ,"Bamboo.inc" , "www.bamboo.com", "123", "123");
                    s.agregarProveedor("Jorge", "Jorge@gmail.com", "Jorge", "Urrutia", fecha3 ,"Google.inc" , "www.google.com", "123", "123");
                    
                   
                    
                    Producto p1 = new Producto("Pelota", "Pelota inflable ideal", 120, 1,"Lalala", prov, 2);
                    s.agregarProducto("Pelota", 1, "Pelota inflable ideal", "Increible", 120, "Perez", 2);
                    s.agregarProducto("Cargador", 2, "Cargador tipo c", "Muy bueno", 220, "Perez", 20);
                    s.agregarProducto("Sillon Comodo", 3, "Sillon comodo para todos los hogares", "Muy bueno", 330, "Jorge", 25);
                    
                    s.agregarProductoCategoria("Tecno", 1);
                    s.agregarProductoCategoria("Otros", 1);
                    s.agregarProductoCategoria("Tecno", 2);
                    s.agregarProductoCategoria("Bazar", 3);
                    //s.agregarImagenesProducto("Tecno", 2, new File("./imagenes/cargador1.jpg"));
                    //s.agregarImagenesProducto("Tecno", 2, new File("./imagenes/cargador2.jpg"));
                    
                    //s.agregarImagenesProducto("Tecno", 1, new File("./imagenes/pelota1.jpg"));
                    //s.agregarImagenesProducto("Tecno", 1, new File("./imagenes/pelota2.jpg"));
                    
                    //s.agregarImagenesProducto("Bazar", 3, new File("./imagenes/s1.jpg"));
                    //s.agregarImagenesProducto("Bazar", 3, new File("./imagenes/s2.jpg"));
                    
                    s.agregarProductoCategoria("Tecno", 2);
                    //OrdenDeCompra o1 = new OrdenDeCompra(1);
                    //o1.addItem(p1, 3);
                    
                    
                    //s.addOrdenes(o1, "Juan123");

                  
                    Presentacion window = new Presentacion();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Presentacion() {
    	//
    	
    	
    	fileChooser = new JFileChooser();
    	calendar = Calendar.getInstance();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*frame.getContentPane().setLayout(null);*/
        frame.getContentPane().setLayout(new BorderLayout());
        centerFrame(frame);

        // Crear el JDesktopPane
        desktopPane = new JDesktopPane();
        desktopPane.setBounds(0, 0, 900, 700);
        frame.getContentPane().add(desktopPane);

        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Crear menú "Casos de Uso"
        JMenu mnProductos = new JMenu("Productos");
        JMenu mnUsuarios = new JMenu("Usuarios");
        JMenu mnOrdenes = new JMenu("Ordenes");
        menuBar.add(mnProductos);
        menuBar.add(mnUsuarios);
        menuBar.add(mnOrdenes);

        

        // Crear opción "Registrar Usuario"
        JMenuItem mntmRegistrarUsuario = new JMenuItem("Registrar Usuario");
        mntmRegistrarUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Crear e inicializar la ventana secundaria (JInternalFrame)
                JInternalFrame ventanaSecundaria = new JInternalFrame("Registrar Usuario", true, true, true, true);
                ventanaSecundaria.setSize(400, 600); // Ajustar el tamaño
                ventanaSecundaria.setTitle("Alta al usuario");
                ventanaSecundaria.setVisible(true);
                

                // Panel del formulario
                JPanel panel = new JPanel();
                panel.setLayout(null); 
                

                // Crear y agregar componentes al panel
                JLabel nicknameLabel = new JLabel("Nickname:");
                nicknameLabel.setBounds(20, 20, 80, 25);
                panel.add(nicknameLabel);

                JTextField nicknameField = new JTextField(15);
                nicknameField.setBounds(100, 20, 160, 25);
                panel.add(nicknameField);

                JLabel correoLabel = new JLabel("Correo:");
                correoLabel.setBounds(20, 60, 80, 25);
                panel.add(correoLabel);

                JTextField correoField = new JTextField(15);
                correoField.setBounds(100, 60, 160, 25);
                panel.add(correoField);

                JLabel nombreLabel = new JLabel("Nombre:");
                nombreLabel.setBounds(20, 100, 80, 25);
                panel.add(nombreLabel);

                JTextField nombreField = new JTextField(15);
                nombreField.setBounds(100, 100, 160, 25);
                panel.add(nombreField);

                JLabel apellidoLabel = new JLabel("Apellido:");
                apellidoLabel.setBounds(20, 140, 80, 25);
                panel.add(apellidoLabel);

                JTextField apellidoField = new JTextField(15);
                apellidoField.setBounds(100, 140, 160, 25);
                panel.add(apellidoField);
                
                

                // ComboBox
                JLabel tipoUsuarioLabel = new JLabel("Usuario: ");
                tipoUsuarioLabel.setBounds(20, 180, 100, 25);
                panel.add(tipoUsuarioLabel);

                JComboBox<String> tipoUsuarioComboBox = new JComboBox<>(new String[]{"Cliente", "Proveedor"});
                tipoUsuarioComboBox.setBounds(100, 180, 160, 25);
                panel.add(tipoUsuarioComboBox);


                JLabel companiaLabel = new JLabel("Compañía:");
                companiaLabel.setBounds(20, 220, 80, 25);
                panel.add(companiaLabel);

                JTextField companiaField = new JTextField(20);
                companiaField.setBounds(100, 220, 160, 25);
                companiaField.setEnabled(false); 
                panel.add(companiaField);

                JLabel webLabel = new JLabel("Sitio Web:");
                webLabel.setBounds(20, 260, 80, 25);
                panel.add(webLabel);

                JTextField webField = new JTextField(20);
                webField.setBounds(100, 260, 160, 25);
                webField.setEnabled(false);
                panel.add(webField);
                
             // JCalendar     
                JLabel dateLabel = new JLabel("Fecha nacimiento: ");
                dateLabel.setBounds(20, 300, 80, 25);
                panel.add(dateLabel);
                JDateChooser chooser = new JDateChooser();
                chooser.setBounds(100, 300, 80, 25);
                panel.add(chooser);
                
                
             // Escuchar cambios en la fecha seleccionada
                chooser.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("date".equals(evt.getPropertyName())) {
                            Date selectedDate = (Date) evt.getNewValue();
                            if (selectedDate != null) {
                                calendar = Calendar.getInstance();
                                calendar.setTime(selectedDate);


                            } else {
                            	calendar = null;
                            }
                        }
                    }
                });
                
                JLabel contraseniaLabel = new JLabel("Contraseña:");
                contraseniaLabel.setBounds(20, 340, 80, 25);
                panel.add(contraseniaLabel);

                JTextField contraseniaField = new JTextField(20);
                contraseniaField.setBounds(100, 340, 160, 25);
                panel.add(contraseniaField);
                
                JLabel confContraseniaLabel = new JLabel("Confirmar Contraseña:");
                confContraseniaLabel.setBounds(20, 380, 80, 25);
                panel.add(confContraseniaLabel);

                JTextField confContraseniaField = new JTextField(20);
                confContraseniaField.setBounds(100, 380, 160, 25);
                panel.add(confContraseniaField);
            
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
                EntityManager em = emf.createEntityManager();
                
                em.getTransaction().begin();
                
                JButton seleccionarImagenButton = new JButton("Seleccionar Imagen");
                seleccionarImagenButton.setBounds(20, 470, 240, 25);
                panel.add(seleccionarImagenButton);
                
               
                JLabel imagenLabel = new JLabel("No se ha seleccionado ninguna imagen");
                imagenLabel.setBounds(20, 500, 240, 25);
                panel.add(imagenLabel);
                
                

                JButton registrarButton = new JButton("Registrar");
                registrarButton.setBounds(20, 540, 240, 25);
                panel.add(registrarButton);
                
                
                
             // Inicializar JFileChooser
                fileChooser = new JFileChooser();

                // Acción del botón de seleccionar imagen
                seleccionarImagenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e1) {
                        int returnValue = fileChooser.showOpenDialog(null);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            imagenSeleccionada = fileChooser.getSelectedFile();
                            // Verificar que imagenSeleccionada no sea null
                            if (imagenSeleccionada != null) {
                                String nombreArchivo = imagenSeleccionada.getAbsolutePath();
                                if (nombreArchivo.endsWith(".jpg") || nombreArchivo.endsWith(".png")) {
                                    imagenLabel.setText(imagenSeleccionada.getName());
                                    imagenSelecc = nombreArchivo;
                                } else {
                                    // Mostrar mensaje de error si el archivo no es válido
                                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un archivo con extensión .jpg o .png", "Archivo no válido", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                // Mensaje si no se selecciona un archivo
                                JOptionPane.showMessageDialog(null, "No se seleccionó ningún archivo", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });

                
                
                tipoUsuarioComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e1) {
                        boolean esProveedor = tipoUsuarioComboBox.getSelectedItem().equals("Proveedor");
                        
                        if(esProveedor) {
                        	webField.setEnabled(true);
                        	companiaField.setEnabled(true);
                        } else {
                        	webField.setEnabled(false);
                        	companiaField.setEnabled(false);
                        }
                    }
                });

               
                registrarButton.addActionListener(new ActionListener() {
					@Override
                    public void actionPerformed(ActionEvent e1) {
                        String nickname = nicknameField.getText();
                        String correo = correoField.getText();
                        String nombre = nombreField.getText();
                        String apellido = apellidoField.getText();
                        boolean esProveedor = tipoUsuarioComboBox.getSelectedItem().equals("Proveedor");
                        String compania = companiaField.getText();
                        String web = webField.getText();
                        
                        String contra = contraseniaField.getText();
                        String confContra = confContraseniaField.getText();
                        
                       
                        
                        
                        
                       int dia, mes, anio;
                        if(calendar.getTime() != null) {
                        	mes = calendar.get(Calendar.MONTH);
                        	dia = calendar.get(Calendar.DAY_OF_MONTH) + 1;
                        	anio = calendar.get(Calendar.YEAR);
                        } else {
                        	JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha");
                        	return;
                        }
                         
                        
                        
                        if(contra.isEmpty() || confContra.isEmpty() || nickname.isEmpty() || correo.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
                        	JOptionPane.showMessageDialog(null, "Hay campos vacios.");
                        	return;
                        	
                        } else if(esProveedor && (compania.isEmpty() || web.isEmpty())) {
                        	JOptionPane.showMessageDialog(null, "Hay campos vacios.");
                        	return;
                        }
                        
                        if(!s.validarCorreo(correo)) {
                        	JOptionPane.showMessageDialog(null, "Mal formato de correo");
                        	return;
                        }
                     
                        
                        

                        DTFecha fechaNacimiento = new DTFecha(dia, mes, anio);
                        if(anio == LocalDateTime.now().getYear()) {
                        	JOptionPane.showMessageDialog(null, "Ingrese su fecha de nacimiento");
                        	return;
                        }
                        
                        if(esProveedor) {
                        	try {
								s.agregarProveedor(nickname, correo, nombre, apellido, fechaNacimiento, compania, web, contra, confContra);
								
							} catch (UsuarioRepetidoException e) {
								JOptionPane.showMessageDialog(null,e.getMessage());
								return;
							}
                        	Usuario u = s.getUsuario(nickname);
                        	
                        	System.out.println("Imagen seleccionada: " + (imagenSelecc != null));
                        	//if(imagenSelecc != null) {
                        		//u.setImagen(imagenSelecc);
                        	//}
                        	
                        	
                        } else {
                        	try {
								s.agregarCliente(nombre, nickname, apellido, correo, fechaNacimiento, contra, confContra);
							
							} catch (UsuarioRepetidoException e) {
								JOptionPane.showMessageDialog(null,e.getMessage());
								return;
							}
                        	
                        	
                        	s.agregarImagenUsuario(nickname, imagenSelecc);
                        	
                        }
                        
                        em.persist(s.getUsuario(nickname));
                        
                        em.getTransaction().commit();
                        
                        
                        em.close();
                        emf.close();
                        
                        // Limpiar inputs
                        JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
                        nicknameField.setText("");
                        correoField.setText("");
                        nombreField.setText("");
                        apellidoField.setText("");
                        tipoUsuarioComboBox.setSelectedIndex(0);
                        companiaField.setText("");
                        webField.setText("");
                        contraseniaField.setText("");
                        confContraseniaField.setText("");
              //          chooser.setCalendar(null);
                  
                    }
                });

                // Añadir el panel a la ventana secundaria
                ventanaSecundaria.getContentPane().add(panel);
                desktopPane.add(ventanaSecundaria);

                // Opcional: Centrar la ventana secundaria
                ventanaSecundaria.setLocation(100, 100);
            }
        });
        
        
        

        // Crear opción "Mostrar Clientes"
        JMenuItem mntmMostrarClientes = new JMenuItem("Mostrar clientes");
        mntmMostrarClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarClientes();
            }
        });
        
        

        
        
        
        // Alta Categoria
        JMenuItem mntmAltaCategoria = new JMenuItem("Alta Categoria");
        mntmAltaCategoria.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// Crear e inicializar la ventana secundaria (JInternalFrame)
                JInternalFrame ventanaSecundaria = new JInternalFrame("Registrar Usuario", true, true, true, true);
                ventanaSecundaria.setSize(400, 300); // Ajustar el tamaño
                ventanaSecundaria.setTitle("Alta de Categorias");
                ventanaSecundaria.setVisible(true);
                

                // Panel del formulario
                JPanel panel = new JPanel();
                panel.setLayout(null); 
                

                
                JLabel labelNombre = new JLabel("Nombre:");
                labelNombre.setBounds(20, 20, 80, 25);
                panel.add(labelNombre);

                JTextField categoriaField = new JTextField(15);
                categoriaField.setBounds(100, 20, 100, 25);
                panel.add(categoriaField);
                
                JLabel prods = new JLabel("Tiene prods: ");
                prods.setBounds(20, 60, 100, 25);
                panel.add(prods);
                

                JRadioButton prodsSI = new JRadioButton("SI");
                prodsSI.setBounds(120, 60, 60, 25);

                JRadioButton prodsNO = new JRadioButton("NO");
                prodsNO.setBounds(180, 60, 60, 25);
                
                ButtonGroup botonGrupal1 = new ButtonGroup();
                botonGrupal1.add(prodsSI);
                botonGrupal1.add(prodsNO);
                panel.add(prodsSI);
                panel.add(prodsNO);

                JLabel padreLabel = new JLabel("Tiene padre: ");
                padreLabel.setBounds(20, 100, 100, 25);
                panel.add(padreLabel);
                

                JRadioButton padreSI = new JRadioButton("SI");
                padreSI.setBounds(120, 100, 60, 25);

                JRadioButton padreNO = new JRadioButton("NO");
                padreNO.setBounds(180, 100, 60, 25);
                
                ButtonGroup botonGrupal = new ButtonGroup();
                botonGrupal.add(padreSI);
                botonGrupal.add(padreNO);
                panel.add(padreSI);
                panel.add(padreNO);
                
             // ComboBox
                JLabel CategoriasPadreLbael = new JLabel("Padres: ");
                CategoriasPadreLbael.setBounds(20, 140, 100, 25);
                panel.add(CategoriasPadreLbael);

                
                DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(s.listarSoloNombresPadresCat().toArray(new String[0]));
                JComboBox<String> padresCategorias = new JComboBox<>(comboBoxModel);
                padresCategorias.setBounds(100, 140, 160, 25);
                padresCategorias.setEnabled(false);
                panel.add(padresCategorias);
                
                padreSI.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e1) {
                        boolean tienePadre = padreSI.isSelected();
                        
                        if(tienePadre) {
                        	padresCategorias.setEnabled(true);
                        } else {
                        	padresCategorias.setEnabled(false);
                        }
                    }
                });
                
                padreNO.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e1) {
                        boolean tienePadre = padreSI.isSelected();
                        
                        if(tienePadre) {
                        	padresCategorias.setEnabled(true);
                        } else {
                        	padresCategorias.setEnabled(false);
                        }
                    }
                });
                
                JButton registrarButton = new JButton("Registrar");
                registrarButton.setBounds(20, 200, 240, 25);
                panel.add(registrarButton);
                
                
                registrarButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String textCat = categoriaField.getText();
						boolean tieneProds = prodsSI.isSelected();
						boolean tienePadre = padreSI.isSelected();
						String nombreCatPadre = padresCategorias.getSelectedItem().toString();
						
						if(textCat.isEmpty()) {
							JOptionPane.showMessageDialog(null, "El campo de nombre está vacio");
							return;
						}
						
						try {
							s.existeCategoria(textCat);
							
						} catch(Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
							return;
						}
						
						try {
							if(tieneProds) {
								s.agregarCategoriaConProductos(textCat);
								if(tienePadre) {
									s.asignarlePadreCategoria(nombreCatPadre, textCat);
								}
								
							} else {
								s.agregarCategoria(textCat);
								if(tienePadre) {
									s.asignarlePadreCategoria(nombreCatPadre, textCat);
								}
							}
							
						}	catch(Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
							return;
						}
						
						
						
						
						JOptionPane.showMessageDialog(null, "Categoria agregada con exito");
						// Actualizar campos
						categoriaField.setText("");
						botonGrupal.clearSelection();
						botonGrupal1.clearSelection();
						padresCategorias.setEnabled(false);
						
						// Actualizar ComboBox
                        List<String> nuevosPadres = s.listarSoloNombresPadresCat();
                        comboBoxModel.removeAllElements();
                        for (String padre : nuevosPadres) {
                            comboBoxModel.addElement(padre);
                        }
						
						
						
					}
				} );


                // Mostrar la ventana interna
                ventanaSecundaria.getContentPane().add(panel);
                ventanaSecundaria.setVisible(true);

                // Agregar la ventana interna al JDesktopPane
                desktopPane.add(ventanaSecundaria);
                // Opcional: Centrar la ventana interna
                ventanaSecundaria.setLocation(0, 0);
        	}
        });
        
        
        
        
        JMenuItem mntmMostrarOrden = new JMenuItem("Mostrar Ordenes");
        mntmMostrarOrden.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ListarOrdenes ords = new ListarOrdenes("Detalles");
        		desktopPane.add(ords);
        	}
        });
        
        
        
        JMenuItem mntmRegistrarProducto = new JMenuItem("Registrar Producto");
        mntmRegistrarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	RegistrarProducto prod = new RegistrarProducto(null);
            	desktopPane.add(prod);
            }
        });
        
        
        JMenuItem mntmOrdenCompra = new JMenuItem("Generar orden compra");
        mntmOrdenCompra.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	CrearOrdenCompra compra = new CrearOrdenCompra();
                	desktopPane.add(compra);
            	}
            });
        
        

        
        //Opcion Mostrar Proveedor
        JMenuItem mntmMostrarProveedor = new JMenuItem("Mostrar Proveedor");
        mntmMostrarProveedor.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		MostrarProveedor();
        	}
        });
        
        JMenuItem mntmListarProductos = new JMenuItem("Listar Productos");
        mntmListarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JInternalFrame ventanaProductos = new JInternalFrame("Lista de Productos", true, true, true, true);
                ventanaProductos.setSize(400, 500);
                ventanaProductos.setLayout(new BorderLayout());

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                List<DtProducto> listaP = new ArrayList<>();
                try {
                    listaP = s.listarALLProductos();
                } catch (ProductoException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                    return;
                }

                JPanel productosPanel = new JPanel();
                
                
                productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));
                productosPanel.add(new JLabel("Listado de Productos"));
                for (DtProducto dt : listaP) {
                	JLabel productoDT = new JLabel(dt.getNombre() + " - " + dt.getPrecio());
                	productoDT.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent e) {
							JInternalFrame ventanaDetalleProducto = new JInternalFrame("Detalle de Producto", true, true, true, true);
                            ventanaDetalleProducto.setSize(600, 400);
                            ventanaDetalleProducto.setLayout(new BorderLayout());

                            JPanel detallePanel = new JPanel();
                            detallePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                            
                            detallePanel.add(createLabelValuePair("Número de Referencia:", dt.getNumRef().toString()));
                            detallePanel.add(createLabelValuePair("Nombre:", dt.getNombre()));
                            detallePanel.add(createLabelValuePair("Descripción:", dt.getDescripcion()));
                            detallePanel.add(createLabelValuePair("Especificaciones:", dt.getEspecs()));
                            detallePanel.add(createLabelValuePair("Precio: ", String.valueOf(dt.getPrecio())));
                            detallePanel.add(createLabelValuePair("Proveedor:", dt.getNombreProveedor()));
                            detallePanel.add(createLabelValuePair("Stock: ", String.valueOf(dt.getStock()))); 
                            detallePanel.add(new JLabel("=================================================="));
                            detallePanel.add(new JLabel("<html><br />Categorias de los productos: " + dt.getCategorias()));

                            List<String> imagenes = dt.getImagenes();
                            if (imagenes != null && !imagenes.isEmpty()) {
                            	JPanel imagePanel = new JPanel();
                                imagePanel.setLayout(new GridLayout(0, 2, 10, 10));

                                for (String imagenFile : imagenes) {
                                    try {
                                        ImageIcon imageIcon = new ImageIcon(imagenFile);
                                        Image imagenAjuste = imageIcon.getImage();
                                        Image reajuste = imagenAjuste.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                                        ImageIcon imagenIconAjustada = new ImageIcon(reajuste);
                                        JLabel imageLabel = new JLabel(imagenIconAjustada);
                                        imagePanel.add(imageLabel);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }

                                JScrollPane scrollPane = new JScrollPane(imagePanel);
                                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                                scrollPane.setPreferredSize(new Dimension(550, 250));

                                detallePanel.add(scrollPane);
                            }
                            
                            ventanaDetalleProducto.getContentPane().add(detallePanel, BorderLayout.CENTER);
                            ventanaDetalleProducto.setVisible(true);
                            desktopPane.add(ventanaDetalleProducto);
                            ventanaDetalleProducto.setLocation(150, 150);
							
						}

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
					});
                    productosPanel.add(productoDT);
                }
                panel.add(productosPanel, BorderLayout.NORTH);

                JLabel lblCategoria = new JLabel("Categoría:");
                panel.add(lblCategoria, BorderLayout.WEST);
                
                DefaultMutableTreeNode root = s.arbolProductos();
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setLocation(73, 56);

                scrollPane.setSize(200, 100);
                scrollPane.setVisible(true);
                panel.add(scrollPane);
                
                DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
					private static final long serialVersionUID = 1L;
					Icon closedIcon = new ImageIcon("./imagenes/sinElementos.png");

                    @Override
                    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                                  boolean expanded, boolean leaf, int row, boolean hasFocus) {
                        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                        if(value.toString() == "Sin Elementos") {
                        	setIcon(closedIcon);
                        }

                        return this;
                    }
                };
                JTree tree = new JTree(root);
                scrollPane.setViewportView(tree);
                tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
                tree.setCellRenderer(renderer);
                tree.clearSelection();
                

                // Listener para selección de nodos
                tree.addTreeSelectionListener((TreeSelectionListener) new TreeSelectionListener() {
                    public void valueChanged(TreeSelectionEvent event) {
                    	System.out.print("1");
                    	
                    	
                        DefaultMutableTreeNode seleccionado = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                        System.out.print(seleccionado);
                        
                        if(seleccionado == null || seleccionado.getUserObject().toString() == "Cats") {
                        	 productosPanel.removeAll();
                             productosPanel.add(new JLabel("Listado de Productos"));
                             List<DtProducto> listaPr = new ArrayList<>();
							try {
								listaPr = s.listarALLProductos();
							} catch (ProductoException e) {
								JOptionPane.showMessageDialog(null, "Ocurrio un error, volver a intentar");
							}
                             for (DtProducto dt : listaPr) {
                                 productosPanel.add(new JLabel(dt.getNombre() + " - " + dt.getPrecio()));
                             }
                            productosPanel.revalidate();
                         	productosPanel.repaint();
                        	return;
                        }
                        String nombreCategoria = seleccionado.getUserObject().toString();
                        List<DtProducto> prodsFiltrados = new ArrayList<>();
                        List<Integer> numRefs = new ArrayList<Integer>();
                        
                        if(!s.esPadre(nombreCategoria)) {
                        	
                        	try {
								s.comprobarCat(nombreCategoria);
							} catch (CategoriaException e) {
								return;
							}
                        	
                        		
                        	try {
								prodsFiltrados = s.listarProductosPorCategoria(nombreCategoria);
								productosPanel.removeAll();
	                            productosPanel.add(new JLabel("Listado de Productos"));
	                            for (DtProducto dt : prodsFiltrados) {
	                            	if(!numRefs.contains(dt.getNumRef())) {
	                            		numRefs.add(dt.getNumRef());
	                            		productosPanel.add(new JLabel(dt.getNombre() + " - " + dt.getPrecio()));
	                            	}
	                             }
							} catch (ProductoException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
								return;
							}
                        	
                        	
                        		
                        	
                        }
                        
                        productosPanel.revalidate();
                    	productosPanel.repaint();
                        
                    }
                    
                    
                });

                
                
                        tree.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                // Obtener la ruta del nodo donde se hizo clic
                                TreePath path = tree.getPathForLocation(e.getX(), e.getY());

                                if (path != null) {
                                    // Obtener el nodo seleccionado
                                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

                                    // Verificar si el nodo es una hoja
                                    if (selectedNode.isLeaf()) {
                                    	String selection = (String) selectedNode.getUserObject();
                                        String[] parts = selection.split(" - ");

                                        if (parts.length < 2) {
                                            JOptionPane.showMessageDialog(null, "Selección inválida.");
                                            return;
                                        }

                                        // Suponiendo que el número de referencia está antes de los paréntesis
                                        String numRefStr = parts[1].trim().split(" ")[0]; // Obtiene solo el número antes del primer espacio
                                        int numRef;

                                        try {
                                            numRef = Integer.parseInt(numRefStr);
                                        } catch (NumberFormatException ex) {
                                            JOptionPane.showMessageDialog(null, "Error al parsear el número de referencia.");
                                            return;
                                        }
                                    	DtProducto dt = s.getDtProducto(numRef);
                                    	
                                    	System.out.print(numRef);
                                    	System.out.print(parts.length);
                                    	
                                    	JInternalFrame ventanaDetalleProducto = new JInternalFrame("Detalle de Producto", true, true, true, true);
                                        ventanaDetalleProducto.setSize(600, 400);
                                        ventanaDetalleProducto.setLayout(new BorderLayout());

                                        JPanel detallePanel = new JPanel();
                                        detallePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                                        
                                        detallePanel.add(createLabelValuePair("Número de Referencia:", dt.getNumRef().toString()));
                                        detallePanel.add(createLabelValuePair("Nombre:", dt.getNombre()));
                                        detallePanel.add(createLabelValuePair("Descripción:", dt.getDescripcion()));
                                        detallePanel.add(createLabelValuePair("Especificaciones:", dt.getEspecs()));
                                        detallePanel.add(createLabelValuePair("Precio: ", String.valueOf(dt.getPrecio())));
                                        detallePanel.add(createLabelValuePair("Proveedor:", dt.getNombreProveedor()));
                                        
                                        detallePanel.add(createLabelValuePair("Stock: ", String.valueOf(dt.getStock()))); 
                                        
                                        detallePanel.add(new JLabel("=============================================================\n"));
                                        detallePanel.add(new JLabel("<html><br />Categorias de los productos: " + dt.getCategorias()));
                                        List<String> imagenes = dt.getImagenes();
                                        if (imagenes != null && !imagenes.isEmpty()) {
                                        	JPanel imagePanel = new JPanel();
                                            imagePanel.setLayout(new GridLayout(0, 2, 10, 10)); // 3 columnas, espaciado de 10px

                                            for (String imagenFile : imagenes) {
                                                try {
                                                    ImageIcon imageIcon = new ImageIcon(imagenFile);
                                                    Image imagenAjuste = imageIcon.getImage();
                                                    Image reajuste = imagenAjuste.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                                                    ImageIcon imagenIconAjustada = new ImageIcon(reajuste);
                                                    JLabel imageLabel = new JLabel(imagenIconAjustada);
                                                    imagePanel.add(imageLabel);
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                }
                                            }

                                            JScrollPane scrollPane = new JScrollPane(imagePanel);
                                            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                                            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                                            scrollPane.setPreferredSize(new Dimension(550, 250));

                                            detallePanel.add(scrollPane);
                                        }
                                        
                                        ventanaDetalleProducto.getContentPane().add(detallePanel, BorderLayout.CENTER);
                                        ventanaDetalleProducto.setVisible(true);
                                        desktopPane.add(ventanaDetalleProducto);
                                        ventanaDetalleProducto.setLocation(150, 150);
                                    }
                                }
                            }
                        });
                        
                        productosPanel.revalidate();
                    	productosPanel.repaint();
                        

                ventanaProductos.getContentPane().add(panel, BorderLayout.CENTER);

                ventanaProductos.setVisible(true);
                desktopPane.add(ventanaProductos);
                ventanaProductos.setLocation(100, 100);
            }
        });

		
        
        
        
        JMenuItem mntmModificarProductos = new JMenuItem("Modificar Productos");
        mntmModificarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JInternalFrame ventanaProductos = new JInternalFrame("Modificar Productos", true, true, true, true);
                ventanaProductos.setSize(400, 900);
                ventanaProductos.setLayout(new BorderLayout());

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                JPanel productosPanel = new JPanel();
                

                JLabel lblCategoria = new JLabel("Categoría:");
                panel.add(lblCategoria, BorderLayout.WEST);
                
                DefaultMutableTreeNode root = s.arbolProductos();
                
                @SuppressWarnings("serial")
				DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
                    // Íconos personalizados
                	ImageIcon icon = new ImageIcon("./imagenes/sinElementos.png");
                	Image img = icon.getImage();
                    Image resizedImage = img.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH);
                	Icon closedIcon = new ImageIcon(resizedImage);

                    @Override
                    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                                  boolean expanded, boolean leaf, int row, boolean hasFocus) {
                        // Llamar al método de la superclase para configurar el componente
                        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                        // Modificar el ícono según el tipo de nodo
                        if(value.toString() == "Sin Elementos") {
                        	setIcon(closedIcon);
                        }

                        return this;
                    }
                };
                JTree tree = new JTree(root);
                tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
                tree.setCellRenderer(renderer);
                tree.setBounds(83, 50, 275, 170);
                
                panel.add(tree);
                JScrollPane treeScrollPane = new JScrollPane(tree);
                panel.add(treeScrollPane, BorderLayout.CENTER);
                
                tree.addMouseListener(new MouseAdapter() {
                      @Override
                      public void mouseClicked(MouseEvent e) {
                          // Obtener la ruta del nodo donde se hizo clic
                          TreePath path = tree.getPathForLocation(e.getX(), e.getY());

                          if (path != null) {
                             // Obtener el nodo seleccionado
                             DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

                             // Verificar si el nodo es una hoja
                             if (selectedNode.isLeaf()) {
                            	 // Disparar el evento deseado
                                 String selection = (String) selectedNode.getUserObject();
                                 if(selection == "Sin Elementos") {
                                	 return;
                                 }
                                 String[] parts = selection.split(" - "); 
                                 int numRef = Integer.parseInt(parts[1]);
                                 DtProducto dt = s.getDtProducto(numRef);
                                    	
                                JInternalFrame ventanaDetalleProducto = new JInternalFrame("Detalle de Producto", true, true, true, true);
                                ventanaDetalleProducto.setSize(600, 530);
                                ventanaDetalleProducto.setLayout(new BorderLayout());

                                JPanel detallePanel = new JPanel();
                                detallePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                                
                                detallePanel.add(createLabelValuePair("Número de Referencia:", dt.getNumRef().toString()));
                                detallePanel.add(createLabelValuePair("Nombre:", dt.getNombre()));
                                detallePanel.add(createLabelValuePair("Descripción:", dt.getDescripcion()));
                                detallePanel.add(createLabelValuePair("Especificaciones:", dt.getEspecs()));
                                detallePanel.add(createLabelValuePair("Precio: ", String.valueOf(dt.getPrecio())));
                                detallePanel.add(createLabelValuePair("Proveedor:", dt.getNombreProveedor()));
                               
                                detallePanel.add(createLabelValuePair("Stock: ", String.valueOf(dt.getStock())));         
                                        
                                detallePanel.add(new JLabel("=================================================="));
                                detallePanel.add(new JLabel("<html><br />Categorias de los productos: " + dt.getCategorias()));

                                List<String> imagenes = dt.getImagenes();
                                if (imagenes != null && !imagenes.isEmpty()) {
                                	JPanel imagePanel = new JPanel();
                                    imagePanel.setLayout(new GridLayout(0, 2, 10, 10)); // 3 columnas, espaciado de 10px

                                    for (String imagenFile : imagenes) {
                                        try {
                                            ImageIcon imageIcon = new ImageIcon(imagenFile);
                                            Image imagenAjuste = imageIcon.getImage();
                                            Image reajuste = imagenAjuste.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                                            ImageIcon imagenIconAjustada = new ImageIcon(reajuste);
                                            JLabel imageLabel = new JLabel(imagenIconAjustada);
                                            imagePanel.add(imageLabel);
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }

                                    JScrollPane scrollPane = new JScrollPane(imagePanel);
                                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                                    scrollPane.setPreferredSize(new Dimension(550, 250));

                                    detallePanel.add(scrollPane);
                                }
                                

                                ventanaDetalleProducto.getContentPane().add(detallePanel, BorderLayout.CENTER);
                                ventanaDetalleProducto.setVisible(true);
                                ventanaDetalleProducto.toFront();
                                desktopPane.add(ventanaDetalleProducto);
                                ventanaDetalleProducto.setLocation(150, 150);
                                JButton modificarButton = new JButton("Modificar");
                                modificarButton.setBounds(20, 800, 240, 25);
                                
                                
                                
                                modificarButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent z) {
                                    	
                                    	RegistrarProducto prod = new RegistrarProducto(dt);
                                    	desktopPane.add(prod);
                                    	
                                    	 ventanaDetalleProducto.setVisible(false);
                                     	ventanaProductos.setVisible(false);
                                    }
                                });
                               
                                
                                
                                detallePanel.add(modificarButton);
                             }
                          }
                      }
                });
                        
                productosPanel.revalidate();
                productosPanel.repaint();

                ventanaProductos.getContentPane().add(panel, BorderLayout.CENTER);

                ventanaProductos.setVisible(true);
                desktopPane.add(ventanaProductos);
                ventanaProductos.setLocation(100, 100);
            }
        });

        
        
        JMenuItem mntmCancelarOrden = new JMenuItem("Cancelar Orden de Compra");
        mntmCancelarOrden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ListarOrdenes cancelar = new ListarOrdenes("Eliminar");
            	desktopPane.add(cancelar);
            }
        });
        
        
        
        mnUsuarios.add(mntmRegistrarUsuario);
        mnProductos.add(mntmRegistrarProducto);
        mnProductos.add(mntmAltaCategoria);
        mnOrdenes.add(mntmOrdenCompra);
        mnUsuarios.add(mntmMostrarClientes);
        mnUsuarios.add(mntmMostrarProveedor);
        mnProductos.add(mntmModificarProductos);
        mnProductos.add(mntmListarProductos);
        mnOrdenes.add(mntmCancelarOrden);
        mnOrdenes.add(mntmMostrarOrden);
        
        

    
    }


    private void mostrarClientes() {
        JInternalFrame ventanaClientes = new JInternalFrame("Lista de Clientes", true, true, true, true);
        ventanaClientes.setSize(500, 300);
        ventanaClientes.getContentPane().setLayout(new BorderLayout());

        List<Cliente> listaCliente = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();

        List<DTCliente> clientes = new ArrayList<>();
        for (Cliente c : listaCliente) {
            clientes.add(c.crearDt());
        }
        
        String[] columnNames = {"Nick", "Correo", "Nombre Completo"};

        // Crear los datos para la tabla
        Object[][] data = new Object[clientes.size()][3];
        for (int i = 0; i < clientes.size(); i++) {
            DTCliente cliente = clientes.get(i);
            data[i][0] = cliente.getNick();
            data[i][1] = cliente.getCorreo();
            data[i][2] = cliente.getNombre() + " " + cliente.getApellido();
        }

        JTable table = new JTable(data, columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    DTCliente cliente = clientes.get(row);
                    mostrarDetallesCliente(cliente);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        ventanaClientes.getContentPane().add(scrollPane, BorderLayout.CENTER);

        ventanaClientes.setVisible(true);

        desktopPane.add(ventanaClientes);

        // Centrar la ventana interna
        Dimension screenSize = desktopPane.getSize();
        int x = (screenSize.width - ventanaClientes.getWidth()) / 2;
        int y = (screenSize.height - ventanaClientes.getHeight()) / 2;
        ventanaClientes.setLocation(x, y);
    }
    
    private void mostrarDetallesCliente(DTCliente cliente) {
        JInternalFrame ventanaDetalles = new JInternalFrame("Detalles del Cliente", true, true, true, true);
        ventanaDetalles.setSize(400, 300);
        ventanaDetalles.getContentPane().setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Agregar la información del cliente al panel
        String ruta = cliente.getImagenes();
        
        
        
        if (ruta != null) {
        	ImageIcon icon = new ImageIcon(ruta);
        	Image imagen = icon.getImage();
            Image imagenRedimensionada = imagen.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            
            icon = new ImageIcon(imagenRedimensionada);
            // Crear nuevo ImageIcon con la imagen redimensionada
            JLabel imagenLabel = new JLabel(icon);
            
            panel.add(imagenLabel);
        } else {
            panel.add(new JLabel("No hay imagen disponible"));
        }
        panel.add(new JLabel("Tipo de Usuario: " + cliente.getTipo()));
        panel.add(new JLabel("Mail: " + cliente.getCorreo()));
        panel.add(new JLabel("Nick: " + cliente.getNick()));
        
        panel.add(new JLabel("Nombre Completo: " + cliente.getNombre() + " " + cliente.getApellido()));

        
        
        panel.add(new JLabel("Fecha de Nacimiento: " + cliente.getNacimiento().getDia() + " - " + cliente.getNacimiento().getMes() + " - " + cliente.getNacimiento().getAnio()));
        panel.add(Box.createVerticalStrut(5));
       
        panel.add(new JLabel("Ordenes: "));
        if(cliente.getOrdenes().isEmpty()) {
        	panel.add(new JLabel("   Todavia no existen ordenes"));
        } else {
        	Map<Integer, OrdenDeCompra> ordenesCliente = cliente.getOrdenes();
        	
        	
            for (OrdenDeCompra orden : ordenesCliente.values()) {
                DTOrdenDeCompra dtOrden = orden.crearDT();
                panel.add(new JLabel(dtOrden.toString()));
            }
        }
        
    
        JScrollPane scrollPane = new JScrollPane(panel);

        
        ventanaDetalles.getContentPane().add(scrollPane, BorderLayout.CENTER);

    
        ventanaDetalles.setVisible(true);

     
        desktopPane.add(ventanaDetalles);

        
        try {
            ventanaDetalles.setSelected(true);
            ventanaDetalles.toFront();
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }

        // Opcional: Centrar la ventana interna
        ventanaDetalles.setLocation(150, 150);
    }
    
    private void MostrarProveedor() {
    	JInternalFrame ventanaProveedores = new JInternalFrame("Lista de Proveedores", true, true, true, true);
        ventanaProveedores.setSize(500, 300);
        ventanaProveedores.getContentPane().setLayout(new BorderLayout());
        // Recuperar la lista de proveedores
        List<Proveedor> provs = em.createQuery("SELECT p FROM Proveedor p").getResultList();

        List<DTProveedor> proveedores = new ArrayList<DTProveedor>();
        for(Proveedor p: provs) {
        	System.out.print(p.getNombre());
        	proveedores.add(p.crearDt());
        }
        
        // Definir las columnas de la tabla
        String[] columnNames = {"Nick", "Correo"};

     // Crear datos para la tabla
        Object[][] data = new Object[proveedores.size()][2];
        for (int i = 0; i < proveedores.size(); i++) {
            DTProveedor proveedor = proveedores.get(i);
            data[i][0] = proveedor.getNick();
            data[i][1] = proveedor.getCorreo();
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
                    DTProveedor proveedor = proveedores.get(row);
                    mostrarDetallesProveedor(proveedor);
                }
            }
        });

        // Agregar la tabla al JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        ventanaProveedores.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Mostrar la ventana interna
        ventanaProveedores.setVisible(true);

        // Agregar la ventana interna al JDesktopPane
        desktopPane.add(ventanaProveedores);

        // Opcional: Centrar la ventana interna
        ventanaProveedores.setLocation(100, 100);
    }
    
    
    private void mostrarDetallesProveedor(DTProveedor proveedor) {
    	JInternalFrame ventanaDetalles = new JInternalFrame("Detalles del Proveedor", true, true, true, true);
        ventanaDetalles.setSize(400, 300);
        ventanaDetalles.getContentPane().setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Agregar la información del cliente al panel
        ImageIcon imagenIcon = new ImageIcon(proveedor.getImagen());
        
        
        
        if (imagenIcon != null) {
        	Image imagen = imagenIcon.getImage();
            Image imagenRedimensionada = imagen.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            
            imagenIcon = new ImageIcon(imagenRedimensionada);
            // Crear nuevo ImageIcon con la imagen redimensionada
            JLabel imagenLabel = new JLabel(imagenIcon);
            
            panel.add(imagenLabel);
        } else {
            panel.add(new JLabel("No hay imagen disponible"));
        }
        panel.add(new JLabel("Tipo de Usuario: Proveedor"));
        panel.add(new JLabel("Mail: " + proveedor.getCorreo()));
        panel.add(new JLabel("Nick: " + proveedor.getNick()));
        panel.add(new JLabel("Nombre Completo: " + proveedor.getNombre() + " " + proveedor.getApellido()));
        panel.add(new JLabel("Fecha de Nacimiento: " + proveedor.getNacimiento().getDia() + " - " + proveedor.getNacimiento().getMes() + " - " + proveedor.getNacimiento().getAnio()));
        panel.add(new JLabel("Compañía: " + proveedor.getCompania()));
        panel.add(new JLabel("Link: " + proveedor.getLink()));
        panel.add(Box.createVerticalStrut(5));
       
        
        
    
        JScrollPane scrollPane = new JScrollPane(panel);

        
        ventanaDetalles.getContentPane().add(scrollPane, BorderLayout.CENTER);

    
        ventanaDetalles.setVisible(true);

     
        desktopPane.add(ventanaDetalles);

        
        try {
            ventanaDetalles.setSelected(true);
            ventanaDetalles.toFront();
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }

        // Opcional: Centrar la ventana interna
        ventanaDetalles.setLocation(150, 150);
    }



    // Método para centrar el JFrame en la pantalla
    private static void centerFrame(JFrame frame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Dimension frameSize = frame.getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        frame.setLocation(x, y);
    }
    
    private JPanel createLabelValuePair(String labelText, String valueText) {
    	JPanel pairPanel = new JPanel();
        pairPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel label = new JLabel(labelText);
        

       
        JTextField textField = new JTextField(valueText);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(200, 20)); 
        textField.setBackground(Color.WHITE);
        pairPanel.add(label);
        pairPanel.add(textField);
        

        return pairPanel;
    }
}
