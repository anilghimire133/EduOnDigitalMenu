package com.example.ayush.digitalmenu.Checkout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.digitalmenu.ItemDetail.PojoClass;
import com.example.ayush.digitalmenu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    public Context mcontext;
    public ArrayList<PojoClass> datalist;
    public CheckoutActivity checkoutActivity;

    public CheckoutAdapter(Context context, ArrayList<PojoClass> list) {

        this.mcontext = context;
        this.datalist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mcontext).inflate(R.layout.cart_item_layout2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        PojoClass pojo = datalist.get(position);

        String name = pojo.getName();
        String price = pojo.getPrice();
        String quantity = pojo.getQuantity();
        String total = pojo.getTotal();
        String image = pojo.getImage();
        String subtotal = pojo.getSubtotal();
        String vats = pojo.getVat();
        String grandtotal = pojo.getGrandtotal();

        holder.ItemName.setText(name);
        holder.Price.setText(price);
        holder.Quantity.setText(quantity);
        holder.Total.setText(total);
        Picasso.with(mcontext).load(image).fit().centerInside().noFade().placeholder(R.mipmap.burgerdetailimg).into(holder.ImageView);
//---------------- Use of Intent -----------------//
        Intent intent = ((Activity)mcontext).getIntent();

        checkoutActivity.sub_tot.setText(subtotal);
        checkoutActivity.vat.setText(intent.getStringExtra("VAT"));
        checkoutActivity.grand_tot.setText(intent.getStringExtra("GRANDTOTAL"));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ItemName, Price, Quantity, Total;
        private final ImageView ImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.item_name);
            Price = itemView.findViewById(R.id.item_price);
            Quantity = itemView.findViewById(R.id.items_quantity);
            Total = itemView.findViewById(R.id.item_total);
            ImageView = itemView.findViewById(R.id.items_image);

        }
    }
}