/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;


import com.yoni.javaworkshopprojectserver.models.Order;
import com.yoni.javaworkshopprojectserver.models.OrderSummary;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.List;


// todo - fill in

/**
 *
 * @author Yoni
 */
@Singleton
@LocalBean
public class OrdersService extends BaseService {

    public List<OrderSummary> getPagedOrderSummariesByUserId(int start, int amount, int userId){
        return null;// todo - come back to this
    }

    public List<Order> getAllOrders(){
        // todo - first test getting all orders, then test getting by user id
        return getEntityManager()
                .createNamedQuery("Orders.findAll", Order.class)
                .getResultList();
    }
   
}
