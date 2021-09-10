/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.models.CatalogProduct;
import com.yoni.javaworkshopprojectserver.models.Category;
import com.yoni.javaworkshopprojectserver.models.Product;
import com.yoni.javaworkshopprojectserver.models.Stock;
import com.yoni.javaworkshopprojectserver.utils.CollectionUtils;
import com.yoni.javaworkshopprojectserver.utils.ErrorCodes;
import com.yoni.javaworkshopprojectserver.utils.Result;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.criteria.*;
import java.util.*;

/**
 *
 * @author Yoni
 */
@Singleton
@LocalBean
public class ProductsService extends BaseService {


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

    public Stock getStockById(int id){
        return firstOrNull(getEntityManager()
                .createNamedQuery("Stock.findById", Stock.class)
                .setParameter("id", id)
                .getResultList());
    }

    public List<Stock> getStockByProductIds(List<Integer> productIds){
        return createSelectQuery(Stock.class, (entity, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(productIds != null && !productIds.isEmpty()){
                predicates.add(entity.get("product").get("id").in(productIds));
            }
            return predicates;
        }).getResultList();
    }

    public List<CatalogProduct> getCatalog(){
        return stockListToCatalogList(getEntityManager()
                .createNamedQuery("Stock.findAll", Stock.class)
                .getResultList());
    }

    public List<CatalogProduct> getCatalogByProductIds(List<Integer> productIds){
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

    public List<Category> getCategoriesByIds(List<Integer> ids){
        return getEntityManager()
                .createNamedQuery("Categories.findByIds", Category.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public Stock getStockByProductId(int productId){
        return firstOrNull(getEntityManager()
                .createNamedQuery("Stock.findByProductId", Stock.class)
                .setParameter("productId", productId)
                .getResultList());
    }

    private List<CatalogProduct> stockListToCatalogList(List<Stock> stockList){
        return CollectionUtils.convertCollection(stockList, CatalogProduct::new);
    }

    public Result<Void, Integer> setStockEnabled(boolean isEnabled, int productId){
        Stock stock = getStockByProductId(productId);
        if(stock == null){
            return Result.makeError(ErrorCodes.RESOURCES_NOT_FOUND);
        }

        stock.setEnabled(isEnabled);

        withTransaction(addValidation(stock, () -> getEntityManager().merge(stock)));
        getEntityManager().refresh(stock);

        return Result.makeValue(null);
    }

    public Category insertCategory(String title){
        Category newCategory = new Category();
        newCategory.setTitle(title);
        withTransaction(addValidation(newCategory, () -> getEntityManager().persist(newCategory)));
        getEntityManager().refresh(newCategory);
        return newCategory;
    }

    public Result<CatalogProduct, Integer> insertStockedProduct(String title, String desc, byte[] imageData, List<Integer> categoryIds, int quantity, float price){
        Set<Category> categories = new HashSet<>(getCategoriesByIds(categoryIds));

        if(categories.isEmpty()){
            return Result.makeError(ErrorCodes.PRODUCTS_NO_CATEGORIES);
        }

        Stock newStock = new Stock();
        newStock.setQuantity(quantity);
        newStock.setPrice(price);
        newStock.setEnabled(true);

        Product newProduct = new Product();
        newProduct.setTitle(title);
        newProduct.setDescription(desc);
        newProduct.setImageData(imageData);

        newProduct.setCategories(categories);

        withTransaction(addValidation(newProduct, () -> {
            getEntityManager().persist(newProduct);
            getEntityManager().flush();
            newStock.setProduct(newProduct);
            newProduct.setStock(newStock);
            getEntityManager().flush();
        }));

        getEntityManager().refresh(newProduct);
        getEntityManager().refresh(newStock);

        return Result.makeValue(new CatalogProduct(newStock));

    }

    public Result<CatalogProduct, Integer> updateStockedProduct(int productId, String title, String desc, byte[] imageData, List<Integer> categoryIds, int quantity, float price) {
        Stock stock = getStockByProductId(productId);
        if(stock == null){
            return Result.makeError(ErrorCodes.RESOURCES_NOT_FOUND);
        }

        Set<Category> categories = new HashSet<>(getCategoriesByIds(categoryIds));

        if(categories.isEmpty()){
            return Result.makeError(ErrorCodes.PRODUCTS_NO_CATEGORIES);
        }

        stock.setQuantity(quantity);
        stock.setPrice(price);
        stock.setEnabled(true);

        Product product = stock.getProduct();
        product.setTitle(title);
        product.setDescription(desc);
        product.setImageData(imageData);

        product.setCategories(categories);

        withTransaction(addValidation(stock, () -> {
            getEntityManager().merge(stock);
            getEntityManager().flush();
        }));

        getEntityManager().refresh(stock);

        return Result.makeValue(new CatalogProduct(stock));
    }
}
