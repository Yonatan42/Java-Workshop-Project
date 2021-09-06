/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.EntityManagerSingleton;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;


// todo - fill in

/**
 *
 * @author Yoni
 */
@Singleton
@LocalBean
public class OrdersService extends BaseService {

    @EJB
    private EntityManagerSingleton entityManagerBean;
   

    private EntityManager getEntityManager(){
        return entityManagerBean.getEntityManager();
    }
}
