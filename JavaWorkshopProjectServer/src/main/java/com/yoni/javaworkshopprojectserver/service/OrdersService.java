/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;


import com.yoni.javaworkshopprojectserver.models.Order;
import com.yoni.javaworkshopprojectserver.models.OrderDetails;
import com.yoni.javaworkshopprojectserver.models.OrderSummary;
import com.yoni.javaworkshopprojectserver.utils.CollectionUtils;

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
        return orderListToOrderSummaryList(getEntityManager()
                .createNamedQuery("Orders.findByUserId", Order.class)
                .setParameter("userId", userId)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList());
    }

    public OrderDetails getOrderDetailsById(int id) {
        return firstOrNull(orderListToOrderDetailsList(getEntityManager()
                .createNamedQuery("Orders.findById", Order.class)
                .setParameter("id", id)
                .getResultList()));
    }

    private List<OrderSummary> orderListToOrderSummaryList(List<Order> list){
        return CollectionUtils.convertCollection(list, OrderSummary::new);
    }

    private List<OrderDetails> orderListToOrderDetailsList(List<Order> list){
        return CollectionUtils.convertCollection(list, OrderDetails::new);
    }
}
