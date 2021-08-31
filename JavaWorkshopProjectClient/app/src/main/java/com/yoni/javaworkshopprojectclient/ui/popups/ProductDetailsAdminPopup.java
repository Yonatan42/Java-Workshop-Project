package com.yoni.javaworkshopprojectclient.ui.popups;


import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.TokennedServerCallback;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.customviews.Stepper;
import com.yoni.javaworkshopprojectclient.utils.GlideUtils;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class ProductDetailsAdminPopup extends ProductDetailsPopup {

    private Consumer<Product> onProductCreated;
    private Consumer<Product> onProductChanged;
    private Consumer<Integer> onProductDeleted; // the param is the id of the deleted product

    private ViewGroup buttonsHolder;
    private ImageButton btnEditImage;
    private Button btnReset;
    private Button btnSave;
    private Button btnDelete;
    private Stepper stepperStock;
    private List<ProductCategory> selectedCategories;
    private String newBase64Image;
    private int newStock;

    // new product
    public ProductDetailsAdminPopup(ParentActivity parentActivity, Consumer<Product> onProductCreated){
        super(parentActivity, null);
        this.onProductCreated = onProductCreated;
        getViews();
        UIUtils.setViewsVisible(false, btnDelete, btnReset);
        setUp(parentActivity, null);
    }

    // edit product
    public ProductDetailsAdminPopup(ParentActivity parentActivity, Product product, Consumer<Product> onProductChanged, Consumer<Integer> onProductDeleted){
        super(parentActivity, product);
        this.onProductChanged = onProductChanged;
        this.onProductDeleted = onProductDeleted;
        getViews();
        setUp(parentActivity, product);
    }

    private void getViews(){
        buttonsHolder = layout.findViewById(R.id.products_details_popup_admin_buttons_group);
        btnReset = layout.findViewById(R.id.products_details_popup_btn_reset);
        btnSave = layout.findViewById(R.id.products_details_popup_btn_save);
        btnDelete = layout.findViewById(R.id.products_details_popup_btn_delete);
        btnEditImage = layout.findViewById(R.id.products_details_popup_btn_edit_image);
        stepperStock = layout.findViewById(R.id.products_details_popup_stepper_stock);
    }

    private void setUp(ParentActivity parentActivity, Product product){

        txtTitle.setHint(R.string.products_details_popup_txt_title_hint);
        txtPrice.setHint(R.string.products_details_popup_txt_price_hint);
        txtDesc.setHint(R.string.products_details_popup_txt_desc_hint);
        txtCategories.setHint(R.string.products_details_popup_txt_categories_hint);

        UIUtils.setViewsEnabled(true, txtTitle, txtPrice, txtDesc, txtCategories);
        UIUtils.setViewsVisible(true, buttonsHolder, btnEditImage, stepperStock);

        selectedCategories = product != null && product.getCategories() != null
                ? new ArrayList<>(product.getCategories())
                : new ArrayList<>();
        txtCategories.setOnClickListener(v -> new CategoriesPicker(parentActivity, selectedCategories, newSelectedCategories -> {
            txtCategories.setText(getCategoriesText(newSelectedCategories));
            selectedCategories = newSelectedCategories;
        }).show());

        newBase64Image = product != null ? product.getImageData() : null;
        btnEditImage.setOnClickListener(v -> new GetImagePopup(parentActivity, base64Image -> {
            newBase64Image = base64Image;
            GlideUtils.loadBase64IntoImage(newBase64Image, parentActivity, R.drawable.ic_product_placeholder, ivImage);
        }).show());

        newStock = product != null ? product.getStock() : 0;
        stepperStock.setValue(newStock);
        stepperStock.setOnValueChangedListener((v, newValue, oldValue) -> {
            newStock = (int)newValue;
        });

        btnReset.setOnClickListener(v -> {
            newStock = product != null ? product.getStock() : 0;
            stepperStock.setValue(newStock);
            newBase64Image = product != null ? product.getImageData() : null;
            selectedCategories = product != null && product.getCategories() != null
                    ? new ArrayList<>(product.getCategories())
                    : new ArrayList<>();
            setViews(product);
        });

        btnSave.setOnClickListener(v -> {
            // todo - remove comments after testing
            //List<Integer> selectedCategoryIds = ListUtils.map(selectedCategories, ProductCategory::getId);
            String title = UIUtils.getTrimmedText(txtTitle);
            String desc = UIUtils.getTrimmedText(txtDesc);
            float price = UIUtils.tryGetFloatValue(txtPrice, -1); // todo verify that the value is > 0
            // we have selected categories
            // we have the base64 string in newBase64Image
            // we have the stock string in newStock
            // that should be everything we need for a product


            Consumer<Product> callback;
            Product insertUpdateProduct;
            if(product == null){
                // create mode
                insertUpdateProduct = new Product();
                callback = onProductCreated; // will do notify inserted

            }
            else {
                // edit mode
                insertUpdateProduct = new Product(product);
                callback = onProductChanged;  // will do notify changed
            }

            insertUpdateProduct.setTitle(title);
            insertUpdateProduct.setDescription(desc);
            insertUpdateProduct.setPrice(price);
            insertUpdateProduct.setCategories(selectedCategories);
            insertUpdateProduct.setImageData(newBase64Image);
            insertUpdateProduct.setStock(newStock);

            RemoteServiceManager.getInstance().getProductsService().insertUpdateProduct(insertUpdateProduct,
                    (call, response, result) -> {
                        insertUpdateProduct.replace(result);
                        callback.accept(insertUpdateProduct);
                        dismiss();
                    },
                    (call, responseError) -> {
                        ErrorPopup.createGenericOneOff(parentActivity).show();
                    });
        });

        btnDelete.setOnClickListener(v -> {
            RemoteServiceManager.getInstance().getProductsService().disableProduct(product.getProductId(),
                    (call, response, result) -> {
                        onProductDeleted.accept(result); // will do notify removed
                        dismiss();
                    },
                    (call, responseError) -> {
                        ErrorPopup.createGenericOneOff(parentActivity).show();
                    });
        });
    }

}
