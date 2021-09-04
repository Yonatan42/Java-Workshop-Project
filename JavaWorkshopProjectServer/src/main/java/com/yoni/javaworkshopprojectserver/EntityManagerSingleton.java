/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver;

import com.yoni.javaworkshopprojectserver.utils.Logger;

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

    private static final String TAG = "EntityManagerSingleton";
   
    private EntityManager em;

    public EntityManagerSingleton() {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // the mysql jar needs to be added to the glasfish/libs folder
            em = Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
         } catch (ClassNotFoundException ex) {
             Logger.LogError(TAG, "Linking to MySQL failed", ex);
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