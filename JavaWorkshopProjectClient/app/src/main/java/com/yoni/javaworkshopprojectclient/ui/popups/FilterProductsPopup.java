package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.models.ProductFilter;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.listadapters.CategoriesFilterAdapter;

import java.util.ArrayList;
import java.util.List;


public class FilterProductsPopup extends AlertDialog {

    public FilterProductsPopup(ParentActivity parentActivity, ProductFilter existingFilter, List<ProductCategory> categories, Consumer<ProductFilter> onFilterChanged){
        super(parentActivity);

        View layout = LayoutInflater.from(parentActivity).inflate(R.layout.popup_filter_products, null, false);
        TextView txtText = layout.findViewById(R.id.filter_products_txt_text);
        Spinner spinnerCategories = layout.findViewById(R.id.filter_products_spinner_categories);
        categories = new ArrayList<>(categories);
        categories.add(0, new ProductCategory(0, "any"));
        CategoriesFilterAdapter adapter = new CategoriesFilterAdapter(parentActivity, categories);
        spinnerCategories.setAdapter(adapter);

        if(existingFilter != null){
            String filterText = existingFilter.getText();
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
            if(!newFilter.equals(existingFilter)) {
                onFilterChanged.accept(newFilter);
            }
            dismiss();
        });

        btnClear.setOnClickListener(v -> {
            if(existingFilter != null){
                onFilterChanged.accept(null);
            }
            dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dismiss();
        });



        setView(layout);
    }
}
