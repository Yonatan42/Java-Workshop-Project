package com.yoni.javaworkshopprojectclient.models.uimodels;

import com.yoni.javaworkshopprojectclient.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.List;

public class ExpandableOrder {

    private OrderSummary orderSummary;
    private boolean isExpanded;


    public ExpandableOrder(OrderSummary orderSummary){
        this.orderSummary = orderSummary;
    }

    public OrderSummary getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(OrderSummary orderSummary) {
        this.orderSummary = orderSummary;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }


    public static List<ExpandableOrder> fromOrderSummaries(List<OrderSummary> orders){
        return ListUtils.map(orders, ExpandableOrder::new);
    }

    public static List<OrderSummary> toOrderSummaries(List<ExpandableOrder> orders){
        return ListUtils.map(orders, order -> order.orderSummary);
    }
}
