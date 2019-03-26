package com.example.ayush.digitalmenu.MyCart;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ayush.digitalmenu.DatabaseHelper;
import com.example.ayush.digitalmenu.EmptyCartActivity;
import com.example.ayush.digitalmenu.MainMenu.MainMenuActivity;
import com.example.ayush.digitalmenu.R;

public class MyCartActivity extends AppCompatActivity {

    public static Button button, button2;
    public static TextView sub_tot, vat, grand_tot, vat_amt;

    public static String VAT = "10";

    ProgressBar progressbar;
    ScrollView scrollView;

    DatabaseHelper databaseHelper;
    ContentValues cv;

    private RecyclerView recyclerView;
    public GridLayoutManager gridLayoutManager;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        databaseHelper = new DatabaseHelper(this);

        sub_tot = findViewById(R.id.subtotal);
        vat = findViewById(R.id.vat);
        vat_amt = findViewById(R.id.vat_amount);
        grand_tot = findViewById(R.id.grandtotal_amt);

        scrollView = findViewById(R.id.scrollview);
        scrollView.setFillViewport(true);

        progressbar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler_view);

        adapter = new CartAdapter(this, databaseHelper.getUserList());

//--------------------------------------------- To check if Database is empty or not ----------------------------------------------------------//

        if (adapter != null)
            if (adapter.getItemCount() > 0) {  // Yo getItemCount vaneko Adapter class ma vako method ho, jasma datalist ko size define gareko xa and yehi size ko through hamile empty xa ki xaena check gareko ho

                recyclerView.setAdapter(adapter);
                progressbar.setVisibility(View.GONE);

            } else {

                Intent intent = new Intent(MyCartActivity.this, EmptyCartActivity.class);
                startActivity(intent);
            }
//---------------------------------------------------- Ends over here ---------------------------------------------------------------//

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        button = findViewById(R.id.continue_btn);
        button2 = findViewById(R.id.checkout_btn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("My Cart");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCartActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

    }
    //------------ We can use this without using Adapter, if we have to list the items without listview then we can use this -----------//

//    public void populateUser() {
//
//        ArrayList<PojoClass> list = databaseHelper.getUserList();
//
//        for (int i = 0; i < list.size(); i++) {
//
//            PojoClass info = list.get(i);

//            View view = LayoutInflater.from(this).inflate(R.layout.cart_item_layout, null);
//
//            TextView name = view.findViewById(R.id.item_name);
//            TextView qnty = view.findViewById(R.id.items_quantity);
//            TextView price = view.findViewById(R.id.item_price);
//
//            name.setText(info.getName());
//            qnty.setText(info.getQuantity());
//            price.setText(info.getPrice());
//
//            linearLayout.addView(view);
//        }
//    }

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
