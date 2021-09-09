/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import com.yoni.javaworkshopprojectserver.models.Order;
import com.yoni.javaworkshopprojectserver.models.OrderDetails;
import com.yoni.javaworkshopprojectserver.models.OrderSummary;
import com.yoni.javaworkshopprojectserver.service.OrdersService;
import com.yoni.javaworkshopprojectserver.service.ProductsService;
import com.yoni.javaworkshopprojectserver.service.UsersService;
import com.yoni.javaworkshopprojectserver.utils.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// todo - fill in

/**
 *
 * @author Yoni
 */
@Stateless
@Path("orders")
public class OrdersResource extends AbstractRestResource<Order> {

    private static final String TAG = "OrdersResource";

    @EJB
    private OrdersService ordersService;

    @EJB
    private ProductsService productsService;

    @EJB
    private UsersService usersService;


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
        Logger.logFormat(TAG, "<getPagedOrderSummaries>\nAuthorization: %s\nuserId: %d\npageNum: %d", token, userId, pageNum);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            final int PAGE_SIZE = 10;
            List<OrderSummary> orders = ordersService.getPagedOrderSummariesByUserId(pageNum * PAGE_SIZE, PAGE_SIZE, userId);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(JsonUtils.createResponseJson(t, JsonUtils.convertToJson(orders)))
                    .build();
        })));
    }

    @GET
    @Path("details/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderDetails(
            @HeaderParam("Authorization") String token,
            @PathParam("id") int id
    ){
        Logger.logFormat(TAG, "<getOrderDetails>\nAuthorization: %s\nid: %d", token, id);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            OrderDetails order = ordersService.getOrderDetailsById(id);
            if(order != null) {
                return Response
                        .status(Response.Status.CREATED)
                        .entity(JsonUtils.createResponseJson(t, JsonUtils.convertToJson(order)))
                        .build();
            }
            else{
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(JsonUtils.createResponseJson(t, "nothing found for the given id", ErrorCodes.RESOURCES_NOT_FOUND))
                        .build();
            }
        })));
    }



    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(
            @HeaderParam("Authorization") String token,
            @FormParam("userId") int userId,
            @FormParam("email") String email,
            @FormParam("firstName") String fname,
            @FormParam("lastName") String lname,
            @FormParam("phone") String phone,
            @FormParam("address") String address,
            @FormParam("productIds") List<Integer> productIds,
            @FormParam("productQuantities") List<Integer> productQuantities,
            @FormParam("creditCard") String creditCard,
            @FormParam("cardExpiration") Date cardExpiration,
            @FormParam("cardCVV") String cardCVV
    ){
        // todo - at least removed logging of credit card info
        Logger.logFormat(TAG, "<createOrder>\nAuthorization: %s\nuserId: %d\nemail: %s\nfirstName: %s\nlastName: %s\nphone: %s\naddress: %s\nproductIds: %s\nproductQuantities: %s\ncreditCard %s\ncardExpiration: %tF\ncardCVV: %s", token, userId, email, fname, lname, phone, address, productIds, productQuantities, creditCard, cardExpiration, cardCVV);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - make a method that pretends to verify the credit card info
            Map<Integer, Integer> productMap = new HashMap<>();
            for (int i = 0; i < productIds.size(); i++){
                productMap.put(productIds.get(i), productQuantities.get(i));
            }
            Result<Integer, Integer> orderCreationResult = ordersService.createOrder(usersService.findById(userId), email, fname, lname, phone, address, productsService.getStockByProductIds(productIds), productMap);
            if(orderCreationResult.isValid()) {
                return Response
                        .status(Response.Status.CREATED)
                        .entity(JsonUtils.createResponseJson(t, JsonUtils.convertToJson(orderCreationResult.getValue())))
                        .build();
            }
            else{
                int errorCode = orderCreationResult.getError();
                String errorMessage;
                Response.Status status;
                switch (orderCreationResult.getError()){
                    case ErrorCodes.USERS_NO_SUCH_USER:
                        status = Response.Status.FORBIDDEN;
                        errorMessage = "one or more of the products is no longer available";
                        break;
                    case ErrorCodes.RESOURCES_UNAVAILABLE:
                        status = Response.Status.GONE;
                        errorMessage = "one or more of the products is no longer available";
                        break;
                    case ErrorCodes.ORDERS_EMPTY:
                        status = Response.Status.BAD_REQUEST;
                        errorMessage = "order attempted with no products";
                        break;
                    default:
                        status = Response.Status.INTERNAL_SERVER_ERROR;
                        errorMessage = "an unknown error occurred";
                }
                return Response
                        .status(status)
                        .entity(JsonUtils.createResponseJson(errorMessage, errorCode))
                        .build();
            }

        })));
    }



}
