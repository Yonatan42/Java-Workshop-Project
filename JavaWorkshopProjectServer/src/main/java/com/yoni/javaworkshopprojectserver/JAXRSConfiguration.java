package com.yoni.javaworkshopprojectserver;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.SynchronizationType;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
 
    public static EntityManager EM;
    
    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // the mysql jar needs to be added to the glasfish/libs folder
            EM = Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JAXRSConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
