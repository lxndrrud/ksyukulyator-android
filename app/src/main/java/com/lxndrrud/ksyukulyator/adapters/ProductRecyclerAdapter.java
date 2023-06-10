package com.lxndrrud.ksyukulyator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.lxndrrud.ksyukulyator.R;
import com.lxndrrud.ksyukulyator.domain.Product;

import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Product> products;

    public ProductRecyclerAdapter(Context context, List<Product> products) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ProductRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_list_view, parent, true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductRecyclerAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.titleView.setText(product.getTitle());
        holder.costView.setText(String.valueOf(product.getCost()));
        if (product.getCategory() != null) {
            holder.categoryView.setText(product.getCategory().getTitle());
        } else {
            holder.categoryView.setText("Н/А");
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView titleView, costView, categoryView;
        ViewHolder(View view){
            super(view);
            titleView = view.findViewById(R.id.titleView);
            costView = view.findViewById(R.id.costView);
            categoryView = view.findViewById(R.id.categoryTitleView);
        }
    }
}
