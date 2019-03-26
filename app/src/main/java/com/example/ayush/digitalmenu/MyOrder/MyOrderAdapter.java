package com.example.ayush.digitalmenu.MyOrder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayush.digitalmenu.Checkout.CheckoutAdapter;
import com.example.ayush.digitalmenu.ItemDetail.PojoClass;
import com.example.ayush.digitalmenu.R;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    public Context mcontext;
    public ArrayList<PojoClass> datalist;

    public MyOrderAdapter(Context context, ArrayList<PojoClass> list) {

        this.mcontext = context;
        this.datalist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mcontext).inflate(R.layout.myorder_itemlayout, parent, false);
        return new ViewHolder(itemView);
//        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PojoClass pojo = datalist.get(position);

        String name = pojo.getName();
        String price = pojo.getPrice();
        String quantity = pojo.getQuantity();
//        String total = pojo.getTotal();

        holder.ItemName.setText(name);
        holder.Price.setText(price);
        holder.Quantity.setText(quantity);
//        holder.Total.setText(total);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ItemName, Price, Quantity, Total;

        public ViewHolder(View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.item_name);
            Price = itemView.findViewById(R.id.item_price);
            Quantity = itemView.findViewById(R.id.items_quantity);
            Total = itemView.findViewById(R.id.item_total);

        }
    }
}
