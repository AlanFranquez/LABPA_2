package com.market.svcentral;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestPersistence {

    public static void main(String[] args) {
        // Crear la EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();

        // Crear una transacción
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            // Crear una nueva entidad (asegúrate de que la clase Producto esté definida)
            Proveedor prov = new Proveedor("nom", "String nick", "String ape", "String correo", new DTFecha(12,12,2024), "String comp", "String link", "String contrasena");
        	Producto producto = new Producto("nombre", "descripcion", 23, 1, "lalala", prov, 123);
            // Configura otros atributos según tu clase Producto

            // Persistir la entidad
            em.persist(producto);
            transaction.commit();
            System.out.println("Producto guardado exitosamente.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
