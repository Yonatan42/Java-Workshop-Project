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
import java.util.stream.Collector;
import java.util.stream.Collectors;


// todo - fill in

/**
 *
 * @author Yoni
 */
@Singleton
@LocalBean
public class OrdersService extends BaseService {

    public List<Order> getAllOrders(){
        return getEntityManager()
                .createNamedQuery("Orders.findAll", Order.class)
                .getResultList();
    }

    public List<OrderSummary> getAllOrderSummaries(){
        return orderListToOrderSummaryList(getEntityManager()
                .createNamedQuery("Orders.findAll", Order.class)
                .getResultList());
    }

    public List<OrderSummary> getPagedOrderSummariesByUserId(int start, int amount, int userId){
        // todo - first test getting all orders, then test getting by user id
        return orderListToOrderSummaryList(getEntityManager()
                .createNamedQuery("Orders.findByUserId", Order.class)
                .setParameter("userId", userId)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList());
    }

    private List<OrderSummary> orderListToOrderSummaryList(List<Order> list){
        return convertList(list, OrderSummary::new);
    }
   
}
