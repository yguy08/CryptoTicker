package com.tapereader.framework;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.tapereader.model.Tip;

public class TickJPATest {

    public static void main(String[] args) {
        EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory( "com.tapereader" );
        
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist( new Tick( "BTC/USD", "FAKE", 1000L, 1000.00 ) );
        entityManager.persist( new Tick( "BTC/USD", "FAKE", 2000L, 2000.00 ) );
        entityManager.getTransaction().commit();
        entityManager.close();
        
        entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist( new Tip(  ) );
        entityManager.persist( new Tip(  ) );
        entityManager.getTransaction().commit();
        entityManager.close();

        // now lets pull events from the database and list them
        entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List result = entityManager.createQuery( "from Tick" ).getResultList();
        for ( Tick event : (List<Tick>) result ) {
            System.out.println( event.toString() );
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        
        sessionFactory.close();

    }

}
