package com.example.ayush.digitalmenu.ItemsPrice;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayush.digitalmenu.DatabaseHelper;
import com.example.ayush.digitalmenu.ItemDetail.ItemDetailActivity;
import com.example.ayush.digitalmenu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemPriceAdapter extends RecyclerView.Adapter<ItemPriceAdapter.ViewHolder> {

    private List<ItemPricePojo> datalist;
    private Context mcontext;
    private OnItemClickListener mlistener;
    ItemDetailActivity itemDetailActivity;
    DatabaseHelper helper;

    int count = 0;
    int likescount = 0;

    public ItemsPriceActivity itemsPriceActivity = new ItemsPriceActivity();

    public interface OnItemClickListener {  // Yo OnItemClickListener lae ItemsPriceActivity ma implement gareko ho
        void onItemClick(ItemPricePojo userInfo);
    }


    public void setOnItemClickListener(OnItemClickListener listener) { // Yo setOnItemClickListener lae pani uta ko Activity ma call garkeo ho
        mlistener = listener;
    }

    public ItemPriceAdapter(Context context, List<ItemPricePojo> list) {

        this.mcontext = context;
        this.datalist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mcontext).inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ItemPricePojo info = datalist.get(position);

        final String name = info.getName();
        final String price = info.getPrice();
        final String id = info.getMenu_Id();

        holder.name.setText(name);
        holder.price.setText(price);

        helper = new DatabaseHelper(mcontext);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                final Animation animShake = AnimationUtils.loadAnimation(mcontext, R.anim.shake);
//                itemsPriceActivity.totalcart.startAnimation(animShake);

                if (holder.checkBox.isChecked()){

                    customalertdialogue();

                    count++;
                    itemsPriceActivity.totalcart.setText(Integer.toString(count));

                    ContentValues cv = new ContentValues();
                    cv.put("Name", name);
                    cv.put("Menu_Id", id);
                    cv.put("Price", price);
                    cv.put("Quantity", "1");
                    cv.put("Total", price);
                    cv.put("Likes", "1");

//                    Toast.makeText(mcontext, id+"", Toast.LENGTH_SHORT).show();
                    helper.insertUser(cv);
                }

                else {
                    count--;
                    itemsPriceActivity.totalcart.setText(Integer.toString(count));
                    helper.deleteUser(id);
                }
           }
        });

    }

    public void customalertdialogue(){

        Dialog mydialog = new Dialog(mcontext);
        mydialog.setContentView(R.layout.quantitydialoguebox);
        mydialog.setTitle("Quantity");

        mydialog.show();
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView name, price, totalcart;
        private final CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.text1);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.items_image);
            totalcart = itemView.findViewById(R.id.totalcartitems);
            checkBox = itemView.findViewById(R.id.addtocart);

        }
    }

}
