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
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Yoni
 */
@Singleton
@LocalBean
public class ProductsService extends BaseService {

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

    public List<CatalogProduct> getActivePagedProductsFiltered(int start, int amount, String filterTitle, Integer filterCategoryId){

        // todo - remove this once we can test the server again.
//        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
//        CriteriaQuery<Stock> criteriaQuery = builder.createQuery(Stock.class);
//        Root<Stock> entity = criteriaQuery.from(Stock.class);
//        criteriaQuery.select(entity);
//        List<Predicate> predicates = new ArrayList<>();
//        if(filterTitle != null && !filterTitle.isEmpty()) {
//            predicates.add(builder.like(builder.lower(entity.get("product").get("title")), "%"+filterTitle.toLowerCase()+"%"));
//        }
//        if(filterCategoryId != null) {
//            predicates.add(builder.equal(entity.join("product").join("categories").get("id"), filterCategoryId));
//        }
//        predicates.add(builder.isTrue(entity.get("isEnabled")));
//        predicates.add(builder.greaterThan(entity.get("quantity"),0));
//        criteriaQuery.where(predicates.toArray(new Predicate[0]));
//        Query query = getEntityManager().createQuery(criteriaQuery);
//        query.setFirstResult(start);
//        query.setMaxResults(amount);
//        return stockListToCatalogList(query.getResultList());

        return stockListToCatalogList(createSelectQuery(Stock.class, (entity, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(filterTitle != null && !filterTitle.isEmpty()) {
                predicates.add(builder.like(builder.lower(entity.get("product").get("title")), "%"+filterTitle.toLowerCase()+"%"));
            }
            if(filterCategoryId != null) {
                predicates.add(builder.equal(entity.join("product").join("categories").get("id"), filterCategoryId));
            }
            predicates.add(builder.isTrue(entity.get("isEnabled")));
            predicates.add(builder.greaterThan(entity.get("quantity"),0));
            return predicates;
        })
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
        // todo - remove this once we can test the server
//        return  stockListToCatalogList((productIds != null && !productIds.isEmpty() ?
//                getEntityManager()
//                        .createNamedQuery("Stock.findByProductIds", Stock.class)
//                        .setParameter("productIds", productIds) :
//                getEntityManager()
//                        .createNamedQuery("Stock.findAll", Stock.class))
//                .getResultList());

        return stockListToCatalogList(createSelectQuery(Stock.class, (entity, builder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if(productIds != null && !productIds.isEmpty()){
                        predicates.add(entity.get("product").get("id").in(productIds));
                    }
                    return predicates;
                })
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
