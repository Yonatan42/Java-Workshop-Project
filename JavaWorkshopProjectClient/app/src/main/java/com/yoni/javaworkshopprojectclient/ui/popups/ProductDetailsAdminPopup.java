package com.yoni.javaworkshopprojectclient.ui.popups;


import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.utils.GlideUtils;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailsAdminPopup extends ProductDetailsPopup {

    private ParentActivity parentActivity;

    private Consumer<Product> onProductCreated;
    private Consumer<Product> onProductChanged;
    private Consumer<Integer> onProductDeleted; // the param is the id of the deleted product

    private ViewGroup buttonsHolder;
    private ImageButton btnEditImage;
    private Button btnReset;
    private Button btnSave;
    private Button btnDelete;
    private List<ProductCategory> selectedCategories;
    private String newBase64Image;

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
    }

    private void setUp(ParentActivity parentActivity, Product product){
        this.parentActivity = parentActivity;

        txtTitle.setHint(R.string.products_details_popup_txt_title_hint);
        txtPrice.setHint(R.string.products_details_popup_txt_price_hint);
        txtDesc.setHint(R.string.products_details_popup_txt_desc_hint);
        txtCategories.setHint(R.string.products_details_popup_txt_categories_hint);

        UIUtils.setViewsEnabled(true, txtTitle, txtPrice, txtDesc, txtCategories);
        UIUtils.setViewsVisible(true, buttonsHolder, btnEditImage);

        selectedCategories = product != null && product.getCategories() != null
                ? new ArrayList<>(product.getCategories())
                : new ArrayList<>();
        txtCategories.setOnClickListener(v -> new CategoriesPicker(parentActivity, selectedCategories, newSelectedCategories -> {
            txtCategories.setText(getCategoriesText(newSelectedCategories));
            selectedCategories = newSelectedCategories;
        }).show());

        btnEditImage.setOnClickListener(v -> new GetImagePopup(parentActivity, base64Image -> {
            newBase64Image = base64Image;
            GlideUtils.loadBase64IntoImage(newBase64Image, parentActivity, R.drawable.ic_product_placeholder, ivImage);
        }).show());

        btnReset.setOnClickListener(v -> {
            newBase64Image = product != null ? product.getImageData() : null;
            selectedCategories = product != null && product.getCategories() != null
                    ? new ArrayList<>(product.getCategories())
                    : new ArrayList<>();
            setViews(product);
        });

        btnSave.setOnClickListener(v -> {
            // todo take current values and upload to server

            List<Integer> selectedCategoryIds = ListUtils.map(selectedCategories, ProductCategory::getId);
            String title = UIUtils.getTrimmedText(txtTitle);
            String desc = UIUtils.getTrimmedText(txtDesc);
            float price = UIUtils.tryGetFloatValue(txtPrice, -1); // todo verify that the value is > 0
            // we have the base64 string in newBase64Image
            // that should be everything we need for a product

            if(onProductCreated != null){
                // create mode
                Product newProduct = new Product(); // todo - this is actually gotten from server
                // -> upload
                onProductCreated.accept(newProduct); // will do notify inserted
            }
            else {
                // edit mode
                Product updatedProduct = new Product(); // todo - this is actually gotten from server
                onProductChanged.accept(updatedProduct); // will do notify changed
            }
        });

        btnDelete.setOnClickListener(v -> {
            // todo - server request to delete passing in the id
            // server will return the id of the deleted product
            int id = 0;// todo - this is actually gotten from server
            onProductDeleted.accept(id); // will do notify removed
        });
    }
}
