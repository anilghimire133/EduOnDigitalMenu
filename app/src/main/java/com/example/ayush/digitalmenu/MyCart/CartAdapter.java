package com.example.ayush.digitalmenu.MyCart;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.digitalmenu.Checkout.CheckoutActivity;
import com.example.ayush.digitalmenu.DatabaseHelper;
import com.example.ayush.digitalmenu.ItemDetail.PojoClass;
import com.example.ayush.digitalmenu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//Note: Below tried to send values to PojoClass so that it can be extract to Checkout Adapter class but it failed so Intent.putExtra function is used to do so. Hence, info/pojo .setVat/.setGrandTotal is failed in all sending below VAT & GrandTotal cases.

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<PojoClass> datalist;

    DatabaseHelper helper;
    ContentValues cv;
    MyCartActivity myCartActivity;

    public CartAdapter(Context context, ArrayList<PojoClass> items) {

        this.mcontext = context;
        this.datalist = items;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(mcontext).inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.ViewHolder holder, final int position) {

        final PojoClass pojo = datalist.get(position);

        final String Id = pojo.getId();
        final String name = pojo.getName();
        final String price = pojo.getPrice();
        final String quantity = pojo.getQuantity();
        final String total = pojo.getTotal();

        final String image = pojo.getImage();
        final String menu_id = pojo.getItem_id();

        holder.ItemName.setText(name);
        holder.Price.setText(price);
        holder.Quantity.setText(quantity);
        holder.Total.setText(total);

        myCartActivity.sub_tot.setText(pojo.getSubtotal()); // page khuldae subtotal ma exact value display garna

        String VAT = get_vatAmount(Double.parseDouble(pojo.getSubtotal()), Integer.parseInt(myCartActivity.VAT));
        pojo.setVat(VAT); //pojo ma pathako so that checkout order bata get garna milos. But yesle kaam gareko xaena & intent.putExtra use gareko xa send garna.
        myCartActivity.vat.setText(pojo.getVat());

        String Grand_Total = get_grand_tot(Double.parseDouble(pojo.getSubtotal()), Double.parseDouble((pojo.getVat())));
        pojo.setGrandtotal(Grand_Total);
        myCartActivity.grand_tot.setText(Grand_Total);

        Picasso.with(mcontext).load(image).fit().centerInside().noFade().placeholder(R.drawable.cart_icon).into(holder.itemImage);

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mcontext, "Item deleted sucessfully!!", Toast.LENGTH_SHORT).show();
//                delete(position);
                Toast.makeText(mcontext, "Id "+menu_id+" deleted sucessfully!!", Toast.LENGTH_SHORT).show();
                helper = new DatabaseHelper(mcontext);
                helper.deleteUser(Id); // yo mathi bata pass gareko id ho, yehi id liyera current click gareko item delete garne kaam garx

                mcontext.startActivity(new Intent(mcontext, MyCartActivity.class)); // Page refresh garauna so y subtotal plus ma click garda updated value aaos.
                ((Activity) mcontext).finish();
            }
        });

//        String VAT = String.valueOf(get_vatAmount(Double.parseDouble(pojo.getSubtotal()), Integer.parseInt(myCartActivity.VAT)));
//        pojo.setVat(VAT); //pojo ma pathako so that checkout order bata get garna milos.
//        myCartActivity.vat.setText(pojo.getVat());
//
//        String Grand_Total = get_grand_tot(Double.parseDouble(pojo.getSubtotal()), Double.parseDouble((pojo.getVat())));
//        pojo.setGrandtotal(Grand_Total);
//        myCartActivity.grand_tot.setText(Grand_Total);

        myCartActivity.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//--------- Used Intent to pass values to CheckoutActivity class -----------//

                Intent intent =(new Intent(mcontext, CheckoutActivity.class));
                intent.putExtra("VAT", pojo.getVat()+"");
                intent.putExtra("GRANDTOTAL", pojo.getGrandtotal()+"");
                mcontext.startActivity(intent);
//                mcontext.startActivity(new Intent(mcontext, CheckoutActivity.class));
            }
        });


        holder.Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(pojo.getQuantity().toString()) < 10) {

                    Integer qnty = Integer.parseInt(pojo.getQuantity());
                    qnty++;
                    pojo.setQuantity(qnty.toString());
                    holder.Quantity.setText(Integer.toString(qnty));

                    String new_total = get_totalPrice(Double.parseDouble(pojo.getPrice()), Integer.parseInt(pojo.getQuantity()));
                    pojo.setTotal(new_total);
                    holder.Total.setText(new_total);

                    cv = new ContentValues();
                    cv.put("Quantity", pojo.getQuantity());
                    cv.put("Total", pojo.getTotal());

                    notifyDataSetChanged();
                    helper = new DatabaseHelper(mcontext);
                    helper.updateUser(cv, Id);

                    myCartActivity.sub_tot.setText(pojo.getSubtotal());

                    //----------- For VAT calculation ----------------//
                    String VAT = String.valueOf(get_vatAmount(Double.parseDouble(pojo.getSubtotal()), Integer.parseInt(myCartActivity.VAT)));
                    PojoClass info = new PojoClass();
                    info.setVat(VAT);
                    myCartActivity.vat.setText(pojo.getVat());
                    //---------- Ends over here ---------------------//


                   // ------------ For Grand Total -----------------//
                    String Grand_Total = get_grand_tot(Double.parseDouble(pojo.getSubtotal()), Double.parseDouble((pojo.getVat())));
                    pojo.setGrandtotal(Grand_Total);
                    myCartActivity.grand_tot.setText(Grand_Total);
                  //  ---------- Ends over here ---------------------//

                    mcontext.startActivity(new Intent(mcontext, MyCartActivity.class)); // Page refresh garauna so y subtotal plus ma click garda updated value aaos.
                    ((Activity) mcontext).finish();

                }
                if (Integer.parseInt(pojo.getQuantity().toString()) > 10) {

                }
            }
        });

        holder.Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(pojo.getQuantity().toString()) > 1) {

                    Integer qnty = Integer.parseInt(pojo.getQuantity().toString());
                    qnty--;
                    pojo.setQuantity(qnty.toString());
                    holder.Quantity.setText(qnty.toString());

                    String new_total = get_totalPrice(Double.parseDouble(pojo.getPrice()), Integer.parseInt(pojo.getQuantity()));
                    pojo.setTotal(new_total);
                    holder.Total.setText(new_total);

                    cv = new ContentValues();
                    cv.put("Quantity", pojo.getQuantity());
                    cv.put("Total", pojo.getTotal());

                    notifyDataSetChanged();
                    helper = new DatabaseHelper(mcontext);
                    helper.updateUser(cv, Id);

                    myCartActivity.sub_tot.setText(pojo.getSubtotal());

                    //----------- For VAT calculation ----------------//
                    String VAT = String.valueOf(get_vatAmount(Double.parseDouble(pojo.getSubtotal()), Integer.parseInt(myCartActivity.VAT)));
                    PojoClass info = new PojoClass();
                    info.setVat(VAT);
                    myCartActivity.vat.setText(pojo.getVat());
                    //---------- Ends over here ---------------------//


                    // ------------ For Grand Total -----------------//
                    String Grand_Total = get_grand_tot(Double.parseDouble(pojo.getSubtotal()), Double.parseDouble((pojo.getVat())));
                    pojo.setGrandtotal(Grand_Total);
                    myCartActivity.grand_tot.setText(Grand_Total);
                    //  ---------- Ends over here ---------------------//

                    mcontext.startActivity(new Intent(mcontext, MyCartActivity.class)); // Page refresh garauna so y subtotal plus ma click garda updated value aaos.
                    ((Activity) mcontext).finish();
                }

                if (Integer.parseInt(pojo.getQuantity().toString()) < 1) {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

//    public void delete(int position) {
//        datalist.remove(position);
//        notifyItemRemoved(position);
//    }

    public String get_totalPrice(Double price, int quantity) {
        Double total;
        total = price * quantity;
        return total.toString();
    }

    public String get_vatAmount(Double subtotal, int vat_percent) {

        Double Sub_tot = Double.valueOf(subtotal * vat_percent / 100);
        return Sub_tot.toString();
    }

    public String get_grand_tot(Double Sub_Total, Double VAT_Amount) {

        Double GRAND_TOTAL = (Sub_Total + VAT_Amount);
        return GRAND_TOTAL.toString();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ItemName, Price, Quantity, Total; // As Subtotal CartActivity ma lie gareko xa not in this adapter xml file so we don't need to use Subtotal here. This is only for the adapter xml items.
        private final Button Plus, Minus, Delete;
        private final ImageView itemImage;

        public ViewHolder(View itemview) {
            super(itemview);

            ItemName = itemview.findViewById(R.id.item_name);
            Price = itemview.findViewById(R.id.item_price);
            Quantity = itemview.findViewById(R.id.items_quantity);
            Total = itemview.findViewById(R.id.item_total);
            itemImage = itemview.findViewById(R.id.items_image);

            Plus = itemview.findViewById(R.id.plusicon);
            Minus = itemview.findViewById(R.id.minusicon);
            Delete = itemview.findViewById(R.id.delete_button);
        }
    }
}