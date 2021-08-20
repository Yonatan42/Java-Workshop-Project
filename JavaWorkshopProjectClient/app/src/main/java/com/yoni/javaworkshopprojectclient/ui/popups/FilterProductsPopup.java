package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.datatransfer.models.ProductFilter;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.listadapters.CategoriesAdapter;

import java.util.List;


public class FilterProductsPopup extends AlertDialog {

    public FilterProductsPopup(ParentActivity parentActivity, ProductFilter existingFilter, List<ProductCategory> categories, Consumer<ProductFilter> onFilter, Runnable onClear){
        super(parentActivity);

        View layout = LayoutInflater.from(parentActivity).inflate(R.layout.popup_filter_products, null, false);
        TextView txtText = layout.findViewById(R.id.filter_products_txt_text);
        Spinner spinnerCategories = layout.findViewById(R.id.filter_products_spinner_categories);
        CategoriesAdapter adapter = new CategoriesAdapter(parentActivity, categories);
        spinnerCategories.setAdapter(adapter);

        if(existingFilter != null){
            String filterText = existingFilter.toString();
            ProductCategory filterCategory = existingFilter.getCategory();
            txtText.setText(filterText != null ? filterText : "");

            int categoryPos = 0;
            if(filterCategory != null){
                for (int i=0; i < categories.size(); i++){
                    if(categories.get(i).getId() == filterCategory.getId()){
                        categoryPos = i;
                        break;
                    }
                }
            }
            spinnerCategories.setSelection(categoryPos,false);
        }


        Button btnFilter = layout.findViewById(R.id.filter_products_btn_filter);
        Button btnClear = layout.findViewById(R.id.filter_products_btn_clear);
        Button btnCancel = layout.findViewById(R.id.filter_products_btn_cancel);


        btnFilter.setOnClickListener(v -> {
            String text = txtText.getText().toString().toLowerCase().trim();
            ProductCategory category = ((ProductCategory)spinnerCategories.getSelectedItem());
            ProductFilter newFilter = new ProductFilter(text, category);
            onFilter.accept(newFilter);
            dismiss();

//            int categoryId = 0;
//            if(category != null){
//                categoryId = category.getId();
//            }
//
//
//            RemoteService.getInstance().getProductsService().getFilteredProducts(
//                    TokenStore.getInstance().getToken(),
//                    categoryId,
//                    text
//                    ).enqueue(new TokennedServerCallback<List<Product>>() {
//                @Override
//                public void onResponseSuccessTokenned(Call<ServerResponse<TokennedResult<List<Product>>>> call, Response<ServerResponse<TokennedResult<List<Product>>>> response, List<Product> result) {
//                    onFilter.accept(result, existingFilter);
//                    dismiss();
//                }
//
//                @Override
//                public void onResponseError(Call<ServerResponse<TokennedResult<List<Product>>>> call, ServerResponse.ServerResponseError responseError) {
//                    // todo - change this
//                    new ErrorPopup(parentActivity, "some death").show();
//                }
//
//                @Override
//                public void onFailure(Call<ServerResponse<TokennedResult<List<Product>>>> call, Throwable t) {
//                    // todo - change this
//                    new ErrorPopup(parentActivity, "some more death").show();
//                }
//            });
        });

        btnClear.setOnClickListener(v -> {
            onClear.run();
            dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dismiss();
        });



        setView(layout);
    }
}
