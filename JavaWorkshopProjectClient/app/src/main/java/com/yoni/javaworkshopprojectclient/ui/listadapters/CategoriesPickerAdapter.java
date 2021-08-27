package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.datatransfer.models.uimodels.SelectableCategory;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.List;

public class CategoriesPickerAdapter extends RecyclerView.Adapter<CategoriesPickerAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final CheckBox checkBox;

        public ViewHolder(View itemView){
            super(itemView);
            checkBox = (CheckBox) itemView;
        }

    }


    private List<SelectableCategory> categories;


    public CategoriesPickerAdapter(List<SelectableCategory> categories){
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        CheckBox checkBox = new CheckBox(context);
//        float textSize = context.getResources().getDimension(R.dimen.standard_text_size);
//        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        UIUtils.setSpText(checkBox, R.dimen.standard_text_size);
        ViewHolder viewHolder = new ViewHolder(checkBox);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectableCategory category = categories.get(position);
        holder.checkBox.setChecked(category.isSelected());
        holder.checkBox.setText(category.getTitle());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            category.setSelected(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


}
