package com.example.ayush.digitalmenu.Checkout;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ayush.digitalmenu.DatabaseHelper;
import com.example.ayush.digitalmenu.MainMenu.MainMenuActivity;
import com.example.ayush.digitalmenu.R;

public class CheckoutActivity extends AppCompatActivity {

    Button button;
    public static TextView sub_tot, vat, grand_tot;

    ProgressBar progressbar;
    GridLayoutManager gridLayoutManager;
    DatabaseHelper databaseHelper;

    private RecyclerView recyclerView;
    private CheckoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        databaseHelper = new DatabaseHelper(this);

        sub_tot = findViewById(R.id.subtotal);
        vat = findViewById(R.id.vat);
        grand_tot = findViewById(R.id.grandtotal_amt);

        button = findViewById(R.id.placeorder_btn);
        recyclerView = findViewById(R.id.recycler_view);
        progressbar = findViewById(R.id.progressBar);
        progressbar.setVisibility(View.VISIBLE);

        adapter = new CheckoutAdapter(this, databaseHelper.getUserList());
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        progressbar.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Checkout Order");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertDialog();
            }
        });
    }

    public void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Success!");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.tick_inside_circle);
        builder.setMessage("Thank you for your order...Your order is accepted and is being proceed");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                databaseHelper.delete_table();

                Intent intent = new Intent(CheckoutActivity.this, MainMenuActivity.class);
                startActivity(intent);


            }
        });
        builder.show();
    }

//    public void showAlertDialog(View view){
//
//        TextView text = new TextView(CheckoutActivity.this);
//
//        text.setText("Success!");
//        text.setGravity(Gravity.CENTER);
//        text.setTextColor(Color.BLUE);
//        text.setPadding(20, 20, 20, 20);
//        text.setTextSize(20);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setIcon(R.drawable.tick_inside_circle);
//        builder.setMessage("Thank You for order...Your order is accepted and is being proceed")
//
//                .setCustomTitle(text)
//                .create();
//                 builder.show();
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
