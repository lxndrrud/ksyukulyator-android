package com.lxndrrud.ksyukulyator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.lxndrrud.ksyukulyator.R;
import com.lxndrrud.ksyukulyator.domain.Product;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    private List<Product> itemArrayList;
    private LayoutInflater layoutInflater;

    public ProductListAdapter(Context aContext, List<Product> listData) {
        this.itemArrayList = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return itemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return itemArrayList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.product_list_view, null);
            holder = new ViewHolder();
            holder.titleView = view.findViewById(R.id.titleView);
            holder.costView = view.findViewById(R.id.costView);
            holder.categoryTitleView = view.findViewById(R.id.categoryTitleView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.titleView.setText(itemArrayList.get(i).getTitle());
        holder.costView.setText(String.valueOf(itemArrayList.get(i).getCost()));
        holder.categoryTitleView.setText(itemArrayList.get(i).getCategory().getTitle());
        holder.productCheckbox.setChecked(false);
        return view;
    }


    static class ViewHolder {
        TextView titleView;
        TextView costView;
        TextView categoryTitleView;
        CheckBox productCheckbox;
    }
}
