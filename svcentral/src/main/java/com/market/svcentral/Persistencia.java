package com.market.svcentral;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Persistencia {
	public static void main(String args[]) {
		
		usuarioRandom r1 = new usuarioRandom("sadadsadsa", 12341);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(r1);
		em.getTransaction().commit();
		
		
		// Retrieve all records
        TypedQuery<usuarioRandom> query = em.createQuery("SELECT u FROM usuarioRandom u", usuarioRandom.class);
        List<usuarioRandom> results = query.getResultList();

        // Print all records
        for (usuarioRandom user : results) {
            System.out.println("Nombre: " + user.getNombre() + ", Edad: " + user.getEdad());
        }
        em.close();
	}
}
