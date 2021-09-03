/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import com.yoni.javaworkshopprojectserver.models.Order;
import com.yoni.javaworkshopprojectserver.models.Product;
import com.yoni.javaworkshopprojectserver.models.ProductCategory;
import com.yoni.javaworkshopprojectserver.service.OrdersService;
import com.yoni.javaworkshopprojectserver.service.ProductsService;
import com.yoni.javaworkshopprojectserver.service.UserService;
import com.yoni.javaworkshopprojectserver.utils.ResponseUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

// todo - fill in

/**
 *
 * @author Yoni
 */
@Stateless
@Path("orders")
public class OrdersResource extends AbstractRestResource<Order> {

    @EJB
    private OrdersService ordersService;

    @EJB
    private UserService userService;


    public OrdersResource() {
        super(Order.class);
    }


    @GET
    @Path("{userId}/summaries/page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPagedOrderSummaries(
            @HeaderParam("Authorization") String token,
            @PathParam("userId") int userId,
            @PathParam("pageNum") int pageNum
    ){
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, (u, t) -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        }));
    }

    @GET
    @Path("details/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderDetails(
            @HeaderParam("Authorization") String token,
            @PathParam("orderId") int orderId
    ){
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, (u, t) -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        }));
    }



    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(
            @HeaderParam("Authorization") String token,
            @FormParam("userId") int userId,
            @FormParam("email") String email,
            @FormParam("firstName") String fname,
            @FormParam("lastName") String lname,
            @FormParam("phone") String phone,
            @FormParam("address") String address,
            @FormParam("creditCard") String creditCard,
            @FormParam("cardExpiration") Date cardExpiration,
            @FormParam("cardCVV") String cardCVV
    ){
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, (u, t) -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        }));
    }



}
