/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import com.yoni.javaworkshopprojectserver.models.CatalogProduct;
import com.yoni.javaworkshopprojectserver.models.Product;
import com.yoni.javaworkshopprojectserver.service.ProductsService;
import com.yoni.javaworkshopprojectserver.service.UsersService;
import com.yoni.javaworkshopprojectserver.utils.*;

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
        final int PAGE_SIZE = 10;
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - fill in
            List<CatalogProduct> products = productsService.getActivePagedProductsFiltered(pageNum * PAGE_SIZE, PAGE_SIZE, filterText, filterCategoryId);
            return Response.status(Response.Status.OK)
                    .entity(JsonUtils.createResponseJson(t, JsonUtils.convertToJson(products)))
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
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(
            @HeaderParam("Authorization") String token,
            @PathParam("id") int id/*,
            @FormParam("product") StockProduct product*/ // todo - figure out why this doesn't work and then uncomment
    ){
        Logger.logFormat(TAG, "<updateProduct>\nAuthorization: %s\nid: %d\nproduct: %s", token, id, null/*todo - update this*/);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
            // todo - fill in
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"not implemented\"}")
                    .build();
        })));
    }


    @PUT
    @Path("{id}/enabled")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setProductEnabled(
            @HeaderParam("Authorization") String token,
            @PathParam("id") int id,
            @FormParam("isEnabled") boolean isEnabled
    ){
        Logger.logFormat(TAG, "<setProductEnabled>\nAuthorization: %s\nid: %d\nisEnabled: %b", token, id, isEnabled);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {

            if(productsService.setStockEnabled(isEnabled, id)){
                return Response
                        .status(Response.Status.OK)
                        .entity(JsonUtils.createResponseJson(t, JsonUtils.convertToJson(id)))
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByIds(
            @HeaderParam("Authorization") String token,
            @QueryParam("ids") List<Integer> ids
    ){
        Logger.logFormat(TAG, "<getProductsByIds>\nAuthorization: %s\nids: %s", token, ids);
        return ResponseLogger.loggedResponse(usersService.authenticateEncapsulated(token, (u, t) -> ResponseUtils.respondSafe(t, () -> {
             List<CatalogProduct> products = productsService.getCatalogByProductIds(ids);
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
