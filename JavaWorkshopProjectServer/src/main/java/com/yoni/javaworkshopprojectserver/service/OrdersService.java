/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.service;


import com.yoni.javaworkshopprojectserver.models.*;
import com.yoni.javaworkshopprojectserver.utils.CollectionUtils;
import com.yoni.javaworkshopprojectserver.utils.ErrorCodes;
import com.yoni.javaworkshopprojectserver.utils.Result;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


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

    public Result<Order, Integer> createOrder(User user, String email, String firstName, String lastName, String phone, String address, List<Stock> stockedProducts, Map<Integer, Integer> productMap) {

        if(user == null){
            return Result.makeError(ErrorCodes.USERS_NO_SUCH_USER);
        }

        if(stockedProducts.isEmpty()){
            return Result.makeError(ErrorCodes.ORDERS_EMPTY);
        }

        if(stockedProducts.size() != productMap.size() ||
                stockedProducts.stream().anyMatch(product -> !product.isEnabled() ||  product.getQuantity() < productMap.get(product.getId()) )){
            return Result.makeError(ErrorCodes.RESOURCES_UNAVAILABLE);
        }

        Order order = new Order();
        order.setEmail(email);
        order.setPhone(phone);
        order.setAddress(address);
        order.setFirstName(firstName);
        order.setLastName(lastName);
        Set<OrderProduct> orderProducts = stockedProducts
                .stream()
                .map(stockedProduct -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(stockedProduct.getProduct());
                    orderProduct.setPriceAtOrder(stockedProduct.getPrice());
                    orderProduct.setQuantity(productMap.get(orderProduct.getProduct().getId()));
                    return orderProduct;
                }).collect(Collectors.toSet());
        order.setOrderProducts(orderProducts);
        order.setUser(user);

        withTransaction(() -> {
            getEntityManager().persist(order);
            getEntityManager().flush();
        });

        getEntityManager().refresh(order);


        return Result.makeValue(order);
    }
}
