package com.yoni.javaworkshopprojectserver;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
 
    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // the mysql jar needs to be added to the glasfish/libs folder
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JAXRSConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
