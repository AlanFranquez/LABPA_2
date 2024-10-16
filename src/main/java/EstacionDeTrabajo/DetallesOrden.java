package EstacionDeTrabajo;

import com.model.*;

import java.awt.BorderLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


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
        
        List<DTItem> lista = orden.listarItems();
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.print(orden.getFecha().format(formatter));
        panel.add(new JLabel("Numero de Orden: " + orden.getNumero()));
        panel.add(new JLabel("Fecha: " + orden.getFecha().format(formatter)));
        
        panel.add(new JLabel("============================="));
        
        if(!s.existeOrden(orden.getNumero())) {
        	JOptionPane.showMessageDialog(null, "Esta orden ya se ha eliminado");
        	return;
        } else {
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
