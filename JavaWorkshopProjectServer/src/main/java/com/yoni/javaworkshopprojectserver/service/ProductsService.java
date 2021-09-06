/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.EntityManagerSingleton;
import com.yoni.javaworkshopprojectserver.models.CatalogProduct;
import com.yoni.javaworkshopprojectserver.models.Category;
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
        return stockListToCatalogList(getEntityManager()
                .createNamedQuery("findAllActive", Stock.class)
                .getResultList());
    }

    public List<Stock> getStock(){
        return getEntityManager()
                .createNamedQuery("Stock.findAll", Stock.class)
                .getResultList();
    }

    public List<Stock> getStockByProductIds(List<Integer> productIds){
        String queryString = productIds != null && !productIds.isEmpty() ?
                "Stock.findByProductIds" :
                "Stock.findAll";
        return getEntityManager()
                .createNamedQuery(queryString, Stock.class)
                .setParameter("productIds", productIds)
                .getResultList();
    }

    public List<CatalogProduct> getCatalog(){
        return stockListToCatalogList(getEntityManager()
                .createNamedQuery("Stock.findAll", Stock.class)
                .getResultList());
    }

    public List<CatalogProduct> getCatalogByProductIds(List<Integer> productIds){
        String queryString = productIds != null && !productIds.isEmpty() ?
                "Stock.findByProductIds" :
                "Stock.findAll";
        return stockListToCatalogList(getEntityManager()
                .createNamedQuery(queryString, Stock.class)
                .setParameter("productIds", productIds)
                .getResultList());
    }

    public List<Category> getAllCategories(){
        return getEntityManager()
                .createNamedQuery("Categories.findAll", Category.class)
                .getResultList();
    }

    private List<CatalogProduct> stockListToCatalogList(List<Stock> stockList){
        return stockList
                .stream()
                .map(CatalogProduct::new)
                .collect(Collectors.toList());
    }
}
