/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Yoni
 */
@Singleton
@LocalBean
@Startup
public class EntityManagerSingleton {
   
    private EntityManager em;

    public EntityManagerSingleton() {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // the mysql jar needs to be added to the glasfish/libs folder
            em = Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JAXRSConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public EntityManager getEntityManager(){
        return em;
    }

    @PreDestroy
    public void closeEMF() {
        em.getEntityManagerFactory().close();
    }
}