package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.datatransfer.models.uimodels.SelectableCategory;

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
        checkBox.setTextSize(context.getResources().getDimension(R.dimen.standard_text_size));
        ViewHolder viewHolder = new ViewHolder(checkBox);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectableCategory category = categories.get(position);
        holder.checkBox.setChecked(category.isSelected());
        holder.checkBox.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


}
