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

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
                .createNamedQuery("Stock.findAllActive", Stock.class)
                .getResultList());
    }

    public List<CatalogProduct> getActivePagedProducts(int start, int amount){
        return stockListToCatalogList(getEntityManager()
                .createNamedQuery("Stock.findAllActive", Stock.class)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList());
    }

    public List<Stock> getStock(){
        return getEntityManager()
                .createNamedQuery("Stock.findAll", Stock.class)
                .getResultList();
    }

    public List<Stock> getStockByProductIds(List<Integer> productIds){
        return (productIds != null && !productIds.isEmpty() ?
                getEntityManager()
                    .createNamedQuery("Stock.findByProductIds", Stock.class)
                    .setParameter("productIds", productIds) :
                getEntityManager()
                        .createNamedQuery("Stock.findAll", Stock.class))
                .getResultList();
    }

    public List<CatalogProduct> getCatalog(){
        return stockListToCatalogList(getEntityManager()
                .createNamedQuery("Stock.findAll", Stock.class)
                .getResultList());
    }

    public List<CatalogProduct> getCatalogByProductIds(List<Integer> productIds){
        return  stockListToCatalogList((productIds != null && !productIds.isEmpty() ?
                getEntityManager()
                        .createNamedQuery("Stock.findByProductIds", Stock.class)
                        .setParameter("productIds", productIds) :
                getEntityManager()
                        .createNamedQuery("Stock.findAll", Stock.class))
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
