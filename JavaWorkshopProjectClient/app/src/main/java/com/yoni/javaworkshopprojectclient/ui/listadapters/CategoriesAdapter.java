package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.datatransfer.models.ProductCategory;

import java.util.List;

public class CategoriesAdapter extends ArrayAdapter<ProductCategory> {

    private final List<ProductCategory> categories;

    public CategoriesAdapter(Context context, List<ProductCategory> categories) {
        super(context, 0, android.R.layout.simple_spinner_item, categories);
        this.categories = categories;
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        setView(position, convertView);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        setView(position, convertView);
        return convertView;
    }

    private void setView(int position, View convertView){
        ProductCategory category = categories.get(position);
        TextView txt = convertView.findViewById(android.R.id.text1);
        txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        txt.setText(category.getTitle());
    }
}
