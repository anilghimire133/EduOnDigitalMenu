package com.example.ayush.digitalmenu.ItemsPrice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayush.digitalmenu.ItemDetail.ItemDetailActivity;
import com.example.ayush.digitalmenu.ItemDetail.PojoClass;
import com.example.ayush.digitalmenu.MainMenu.MainMenuPojo;
import com.example.ayush.digitalmenu.MyCart.MyCartActivity;
import com.example.ayush.digitalmenu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsPriceActivity extends AppCompatActivity implements ItemPriceAdapter.OnItemClickListener {

    ProgressBar progressbar;
    RequestQueue requestQueue;
    private GridLayoutManager gridLayoutManager;

    public String url = "http://ksodari.pythonanywhere.com";

    ImageView imageView, itemsimage;
    public static TextView textView, totalcart, item_name, pricetag;

    public static CheckBox checkBox;

    public static String id;

    private List<ItemPricePojo> list = new ArrayList<ItemPricePojo>();

    private RecyclerView recyclerView;
    private ItemPriceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_price);

        MainMenuPojo mainMenuPojo = (MainMenuPojo) getIntent().getSerializableExtra("EXTRA");
        id = mainMenuPojo.getId();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(mainMenuPojo.getCreator()+"");

        recyclerView = findViewById(R.id.grid_view);
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        progressbar = findViewById(R.id.progressBar);
        progressbar.setVisibility(View.VISIBLE);

//        imageView = findViewById(R.id.imageView1);
        itemsimage = findViewById(R.id.itemsimage1);
        item_name = findViewById(R.id.text1);
        pricetag = findViewById(R.id.price);
        textView = findViewById(R.id.text1);
        checkBox = findViewById(R.id.addtocart);
        totalcart = findViewById(R.id.totalcartitems);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemsPriceActivity.this, MyCartActivity.class);
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    public void jsonParse() {

        //        String fetchurl = getIntent().getStringExtra("fetch_url");

        final String fetchurl = (url+"/api/cat/menus/"+id);

        final StringRequest request = new StringRequest(Request.Method.GET, fetchurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("menus");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

//                       UserInfo2 info = new UserInfo2();

//                        info.setId(patient.getString("orga_orgid"));
//                        info.setName(patient.getString("orga_organame"));
//                        list.add(info);

                        String Description = object.getString("menu_description");
                        String Price = object.getString("menu_price");
                        String Name = object.getString("menu_name");
                        String Menu_id = object.getString("menu_id");

                        String urlfront = "http://ksodari.pythonanywhere.com";
                        String urlback = object.getString("menu_image_url");
//                        String Image = (urlfront + "" + urlback);

                        adapter = new ItemPriceAdapter(ItemsPriceActivity.this, list);
                        list.add(new ItemPricePojo(Menu_id, Price, Name, Description ));
                        adapter.notifyDataSetChanged();

                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(ItemsPriceActivity.this); // yo utako adapter class bata call gareko ho
                        progressbar.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String token = "2cfceef344c54a65b2405acee44bdb22a8a09487";
                String auth_token = "Token " + token;   // "Token e973419cf2d6da5660f8a42f0d6b73ca87f14780"
                params.put("Authorization", auth_token);
                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public void onItemClick(ItemPricePojo userInfo) {

        Intent detailIntent = new Intent(this, ItemDetailActivity.class);

        detailIntent.putExtra("EXTRA_URL", userInfo);
//        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
//        detailIntent.putExtra("userInfo", userInfo);
        startActivity(detailIntent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle Starts", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle Resumes", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle Pauses", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle Stops", "onStop");
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
