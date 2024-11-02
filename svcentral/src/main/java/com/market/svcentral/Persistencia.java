package com.market.svcentral;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Persistencia {
	public static void main(String args[]) {
		
		usuarioRandom r1 = new usuarioRandom("alan", 12341);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(r1);
		em.getTransaction().commit();
		em.close();
		
	}
}
