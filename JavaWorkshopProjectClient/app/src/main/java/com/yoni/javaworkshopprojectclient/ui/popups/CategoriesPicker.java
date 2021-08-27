package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.datatransfer.models.uimodels.SelectableCategory;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.ui.listadapters.CategoriesPickerAdapter;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoriesPicker extends AlertDialog {

    protected CategoriesPicker(Context context, List<ProductCategory> existingSelectedCategories, Consumer<List<ProductCategory>> onCategoriesSelected) {
        super(context, R.style.WrapContentDialog);

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.popup_categories_picker, null, false);
        Button btnBack = layout.findViewById(R.id.categories_picker_popup_btn_back);
        Button btnOk = layout.findViewById(R.id.categories_picker_popup_btn_ok);
        Button btnNew = layout.findViewById(R.id.categories_picker_popup_btn_new);
        EditText txtTitle = layout.findViewById(R.id.categories_picker_popup_txt_new);
        RecyclerView rvCategories = layout.findViewById(R.id.categories_picker_popup_rv);

        List<SelectableCategory> selectableCategories = SelectableCategory.fromProductCategories(DataSets.getInstance().getCategories());

        selectInitialCategories(existingSelectedCategories, selectableCategories);

        CategoriesPickerAdapter adapter = new CategoriesPickerAdapter(selectableCategories);
        rvCategories.setAdapter(adapter);
        rvCategories.setLayoutManager(new LinearLayoutManager(context));

        btnNew.setOnClickListener(v -> {
            String title = txtTitle.getText().toString().trim();
            // todo - server request for new category
            Toast.makeText(context, "new category: "+title, Toast.LENGTH_SHORT).show();
            // on successful response
            ProductCategory productCategory = new ProductCategory(11111,title); // will the the product category returned
            SelectableCategory selectableCategory = new SelectableCategory(productCategory);
            DataSets.getInstance().addCategories(productCategory);
            int nextIndex = selectableCategories.size();
            selectableCategories.add(selectableCategory);
            adapter.notifyItemInserted(nextIndex);
            txtTitle.setText("");
        });

        btnBack.setOnClickListener(v -> dismiss());

        btnOk.setOnClickListener(v -> {
            onCategoriesSelected.accept(SelectableCategory.toProductCategory(ListUtils.filter(selectableCategories, SelectableCategory::isSelected)));
            dismiss();
        });

        setView(layout);
    }

    private void selectInitialCategories(List<ProductCategory> existingSelectedCategories, List<SelectableCategory> selectableCategories) {
        if(existingSelectedCategories != null && !existingSelectedCategories.isEmpty()) {
            List<ProductCategory> existingSelectedCategoriesClone = new ArrayList<>(existingSelectedCategories);
            for (SelectableCategory selectableCategory : selectableCategories) {
                ProductCategory match = ListUtils.getFirstWhere(existingSelectedCategoriesClone, category -> category.getId() == selectableCategory.getProductCategory().getId());
                if (match != null) {
                    existingSelectedCategoriesClone.remove(match);
                    selectableCategory.setSelected(true);
                }
                if (existingSelectedCategoriesClone.isEmpty()) {
                    break;
                }
            }
        }
    }
}
