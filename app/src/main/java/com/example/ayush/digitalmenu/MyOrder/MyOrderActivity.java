package com.example.ayush.digitalmenu.MyOrder;

import android.content.ContentValues;
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

import com.android.volley.RequestQueue;
import com.example.ayush.digitalmenu.DatabaseHelper;
import com.example.ayush.digitalmenu.EmptyOrderActivity;
import com.example.ayush.digitalmenu.R;
import com.example.ayush.digitalmenu.StartingLogin.Starting_LoginActivity;

public class MyOrderActivity extends AppCompatActivity {

    ProgressBar progressbar;
    DatabaseHelper databaseHelper;
    ContentValues cv;

    Button button;
    RequestQueue requestQueue;

    private RecyclerView recyclerView;
    public GridLayoutManager gridLayoutManager;
    private MyOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        databaseHelper = new DatabaseHelper(this);

        progressbar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler_view);

        adapter = new MyOrderAdapter(this, databaseHelper.getUserList());

        //--------------------------------------------- To check if Database is empty or not ----------------------------------------------------------//

        if (adapter != null)
            if (adapter.getItemCount() > 0) {  // Yo getItemCount vaneko Adapter class ma vako method ho, jasma datalist ko size define gareko xa and yehi size ko through hamile empty xa ki xaena check gareko ho

                recyclerView.setAdapter(adapter);
                progressbar.setVisibility(View.GONE);

            } else {

                Intent intent = new Intent(MyOrderActivity.this, EmptyOrderActivity.class);
                startActivity(intent);
            }
//---------------------------------------------------- Ends over here ---------------------------------------------------------------//


        button = findViewById(R.id.billing_button);

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("My Order");
    }


    public void setAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setTitle("Call for Bill");
        builder.setMessage("Are you sure you want to call for bill and end up your connection ?");
        builder.setPositiveButton("Yes, Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(MyOrderActivity.this, Starting_LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
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
