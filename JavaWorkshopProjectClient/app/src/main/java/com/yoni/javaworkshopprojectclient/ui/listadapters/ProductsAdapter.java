package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtTitle;

        public ViewHolder(View itemView){
            super(itemView);

            txtTitle = itemView.findViewById(R.id.products_cell_txt_title);
        }

    }


    private List<Product> products;
//    private Context context;


    public ProductsAdapter(/*Context context,*/ List<Product> products){
//        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.cell_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        TextView txtTitle = holder.txtTitle;
        txtTitle.setText(product.getTitle());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    /*
    todo - remove once I have an example in a logical place

        RecyclerView rvProducts = findViewById(R.id.rv_products);

        List<Product> products = new ArrayList<>();
        products.add(new Product()); // and so on

        ProductsAdapter adapter = new ProductsAdapter(products);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(context));

    */


}
