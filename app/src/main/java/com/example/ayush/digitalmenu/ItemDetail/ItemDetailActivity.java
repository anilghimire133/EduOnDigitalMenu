package com.example.ayush.digitalmenu.ItemDetail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayush.digitalmenu.DatabaseHelper;
import com.example.ayush.digitalmenu.ItemsPrice.ItemPricePojo;
import com.example.ayush.digitalmenu.MyCart.CartAdapter;
import com.example.ayush.digitalmenu.MyCart.MyCartActivity;
import com.example.ayush.digitalmenu.R;
import com.squareup.picasso.Picasso;

public class ItemDetailActivity extends AppCompatActivity {

    public static TextView desc, qnty, textView2, Itemname, Price, Likes;
    ImageView imageView;
    Button button, button1, button2;
    DatabaseHelper databaseHelper;
    CartAdapter cartAdapter;
    Context context;

    public static String Name, Prices, Items_Id;

    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ItemPricePojo itemPricePojo = (ItemPricePojo) getIntent().getSerializableExtra("EXTRA_URL");
        Name = itemPricePojo.getName();
        Prices = itemPricePojo.getPrice();
        Items_Id = itemPricePojo.getMenu_Id();

        /* For likes:

         *SharedPreferences msharedpreference = PreferenceManager.getDefaultSharedPreferences(this);
         *String value = (msharedpreference.getString("SharedPeferences", "1"));
         *
         * */

        databaseHelper = new DatabaseHelper(this);

        setTitle("Hamburger");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button1 = findViewById(R.id.plusicon);
        button2 = findViewById(R.id.minusicon);
        qnty = findViewById(R.id.quantity);
        qnty.setText("1");

        Itemname = findViewById(R.id.Nameofitem);
        Itemname.setText(Name);

        Price = findViewById(R.id.price);
        Price.setText(itemPricePojo.getPrice());

        desc = findViewById(R.id.textView4);
        desc.setText(itemPricePojo.getDescription());

        Likes = findViewById(R.id.no_of_likes);
//        Likes.setText(value);

        imageView = findViewById(R.id.itemsimage);
        final String image = (itemPricePojo.getImage() + "");
        Picasso.with(ItemDetailActivity.this).load(image).fit().centerInside().noFade().placeholder(R.mipmap.burgerdetailimg).into(imageView);

        button = findViewById(R.id.addtocartbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Animation animShake = AnimationUtils.loadAnimation(ItemDetailActivity.this, R.anim.shake);
                textView2 = findViewById(R.id.totalcartitems);
                textView2.startAnimation(animShake);
                textView2.setText(qnty.getText());

                final String itemname, likes;
                final int quantity;
                final double price, total;

                itemname = Itemname.getText().toString();
                price = Double.parseDouble(Price.getText().toString());
                quantity = Integer.parseInt(qnty.getText().toString());
                total = price * quantity;
                likes = Likes.getText().toString();

//                PojoClass pojo = new PojoClass();
//
//                pojo.setQuantity(String.valueOf(quantity));
//                pojo.setTotal(String.valueOf(total));

                ContentValues cv = new ContentValues();
                cv.put("Name", itemname);
                cv.put("Menu_Id", Items_Id);
                cv.put("Price", price);
                cv.put("Quantity", quantity);
                cv.put("Total", total);
                cv.put("Likes", likes);
                cv.put("Image", image);

                databaseHelper.insertUser(cv);

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count < 10) {
                    count++;
                    qnty.setText(Integer.toString(count));
                    qnty.setBackgroundResource(R.drawable.circleitemselector);
                }

                if (count == 10) {
                    qnty.setBackgroundResource(R.drawable.redcircleitemselector);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count > 1) {
                    count--;
                    qnty.setText(Integer.toString(count));
                    qnty.setBackgroundResource(R.drawable.circleitemselector);
                }

                if (count == 1) {
                    qnty.setBackgroundResource(R.drawable.redcircleitemselector);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailActivity.this, MyCartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
