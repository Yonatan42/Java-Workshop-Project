/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.resources;

import com.yoni.javaworkshopprojectserver.models.Product;
import com.yoni.javaworkshopprojectserver.models.ProductCategory;
import com.yoni.javaworkshopprojectserver.service.ProductsService;
import com.yoni.javaworkshopprojectserver.service.UserService;
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

    @EJB
    private ProductsService productsService;

    @EJB
    private UserService userService;


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
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, (u, t) -> {
            // todo - fill in
            return null;
        }));
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
            @FormParam("categories") List<ProductCategory> categories,
            @FormParam("price") float price,
            @FormParam("stockQuantity") int stockQuantity
    ){
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, true, (u, t) -> {
            // todo - fill in
            return null;
        }));
    }

    @PUT
    @Path("{productId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(
            @HeaderParam("Authorization") String token,
            @PathParam("productId") int productId,
            @FormParam("product") Product product
    ){
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, true, (u, t) -> {
            // todo - fill in
            return null;
        }));
    }


    @PATCH
    @Path("{productId}/enabled")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setProductEnabled(
            @HeaderParam("Authorization") String token,
            @PathParam("productId") int productId,
            @FormParam("isEnabled") boolean isEnabled
    ){
        return ResponseUtils.respondSafe(() -> userService.authenticateEncapsulated(token, true, (u, t) -> {
            // todo - fill in
            return null;
        }));
    }


    // todo - implement in server/db
    // example of sql syntax for get all entities that are in a group of ids:
    // ->  SELECT * FROM java_workshop_db.products WHERE id IN (1,2,3,4);
    // for the cart page

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByIds(
            @HeaderParam("Authorization") String token,
            @QueryParam("productIds") List<Integer> productIds
    ){
        // todo - fill in
        return null;
    }

    @POST
    @Path("categories")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory(
            @HeaderParam("Authorization") String token,
            @FormParam("title") String title
    ){
        // todo - fill in
        return null;
    }
}
