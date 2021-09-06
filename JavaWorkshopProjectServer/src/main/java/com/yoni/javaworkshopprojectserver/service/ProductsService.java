/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.EntityManagerSingleton;
import com.yoni.javaworkshopprojectserver.models.CatalogProduct;
import com.yoni.javaworkshopprojectserver.models.Stock;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;


// todo - fill in

/**
 *
 * @author Yoni
 */
@Singleton
@LocalBean
public class ProductsService {

    @EJB
    private EntityManagerSingleton entityManagerBean;
   

    private EntityManager getEntityManager(){
        return entityManagerBean.getEntityManager();
    }

    public List<CatalogProduct> getActiveProducts(){
//        return getEntityManager()
//                .createNamedQuery("CatalogProducts.findAllActive", CatalogProduct.class)
//                .getResultList();
return new ArrayList<CatalogProduct>();
        /*
        Map<Integer, List<StockProduct>> stockMap = getEntityManager()
                .createNamedQuery("StockProducts.findAllActive", StockProduct.class)
                .getResultList()
                .stream()
                .collect(Collectors.groupingBy(StockProduct::getProductId));

         */
    }

    public List<Stock> getStock(){
        return getEntityManager()
                .createNamedQuery("Stock.findAll", Stock.class)
                .getResultList();
    }

    public List<CatalogProduct> getCatalog(){
        return getEntityManager()
                .createNamedQuery("Stock.findAll", Stock.class)
                .getResultList()
                .stream()
                .map(CatalogProduct::new)
                .collect(Collectors.toList());
    }
}
