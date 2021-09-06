/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import com.yoni.javaworkshopprojectserver.models.CatalogProduct;
import com.yoni.javaworkshopprojectserver.models.Product;
import com.yoni.javaworkshopprojectserver.models.Stock;
import com.yoni.javaworkshopprojectserver.service.ProductsService;
import com.yoni.javaworkshopprojectserver.service.UsersService;
import com.yoni.javaworkshopprojectserver.utils.JsonUtils;
import com.yoni.javaworkshopprojectserver.utils.Logger;
import com.yoni.javaworkshopprojectserver.utils.ResponseLogger;
import com.yoni.javaworkshopprojectserver.utils.ResponseUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

// todo - fill in

/**
 *
 * @author Yoni
 */
@Stateless
@Path("products")
public class ProductsResource extends AbstractRestResource<Product> {

    private static final String TAG = "ProductsResource";

    @EJB
    private ProductsService productsService;

    @EJB
    private UsersService usersService;


    public ProductsResource() {
        super(Product.class);
    }

    @GET
    @Path("page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPagedProducts(
            @HeaderParam("Authorization") String token,
            @PathParam("pageNum") int pageNum,
            @QueryParam("filterText") String filterText,
            @QueryParam("filterCategoryId") Integer filterCategoryId
    ) {

        Logger.logFormat(TAG, "<getPagedProducts>\nAuthorization: %s\npageNum: %d\nfilterText: %s\nfilterCategoryId: %d", token, pageNum, filterText, filterCategoryId);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        })));
    }

    // when creating a new product we need to create a new stock row as well for it
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertProduct(
            @HeaderParam("Authorization") String token,
            @FormParam("title") String title,
            @FormParam("description") String desc,
            @FormParam("imageData") String imageData,
//            @FormParam("categories") List<ProductCategory> categories,  // todo - figure out why this doesn't work and then uncomment
            @FormParam("price") float price,
            @FormParam("stockQuantity") int stockQuantity
    ){
        Logger.logFormat(TAG, "<getPagedProducts>\nAuthorization: %s\ntitle: %s\ndescription: %s\nimageData: %s\ncategories %s\nprice: %.2f\nstockQuantity: %d", token, title, desc, imageData, null/*todo - update this*/, price, stockQuantity);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        })));
    }

    @PUT
    @Path("{productId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(
            @HeaderParam("Authorization") String token,
            @PathParam("productId") int productId/*,
            @FormParam("product") StockProduct product*/ // todo - figure out why this doesn't work and then uncomment
    ){
        Logger.logFormat(TAG, "<updateProduct>\nAuthorization: %s\nproductId: %d\nproduct: %s", token, productId, null/*todo - update this*/);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        })));
    }


    @PUT
    @Path("{productId}/enabled")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setProductEnabled(
            @HeaderParam("Authorization") String token,
            @PathParam("productId") int productId,
            @FormParam("isEnabled") boolean isEnabled
    ){
        Logger.logFormat(TAG, "<setProductEnabled>\nAuthorization: %s\nproductId: %d\nisEnabled: %b", token, productId, isEnabled);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        })));
    }


    // todo - implement in server/db
    // example of sql syntax for getCatalog all entities that are in a group of ids:
    // ->  SELECT * FROM java_workshop_db.products WHERE id IN (1,2,3,4);
    // for the cart page

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByIds(
            @HeaderParam("Authorization") String token,
            @QueryParam("productIds") List<Integer> productIds
    ){
        Logger.logFormat(TAG, "<getProductsByIds>\nAuthorization: %s\nproductIds: %s", token, productIds);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - this is not necessarily the place for it, this is just a test
            // todo - uncomment - we are only testing now
//             List<CatalogProduct> products = productsService.getCatalog();
            List<Stock> products = productsService.getStockByProductIds(productIds);
            return Response
                    .status(Response.Status.OK)
                    .entity(JsonUtils.createResponseJson(t, JsonUtils.convertToJson(products)))
                    .build();
        })));
    }

    @POST
    @Path("categories")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory(
            @HeaderParam("Authorization") String token,
            @FormParam("title") String title
    ){
        Logger.logFormat(TAG, "<createCategory>\nAuthorization: %s\ntitle: %s", token, title);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        })));
    }
}
