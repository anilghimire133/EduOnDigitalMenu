package com.example.ayush.digitalmenu.MainMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayush.digitalmenu.AboutUs;
import com.example.ayush.digitalmenu.EmptyCartActivity;
import com.example.ayush.digitalmenu.EmptyOrderActivity;
import com.example.ayush.digitalmenu.Feedback;
import com.example.ayush.digitalmenu.HelpingActivity;
import com.example.ayush.digitalmenu.ItemsPrice.ItemsPriceActivity;
import com.example.ayush.digitalmenu.MyCart.MyCartActivity;
import com.example.ayush.digitalmenu.MyOrder.MyOrderActivity;
import com.example.ayush.digitalmenu.No_Internet;
import com.example.ayush.digitalmenu.R;

import android.support.v7.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity implements MainMenuAdapter.OnItemClickListener, SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_ID = "item_id";

    private RecyclerView mRecyclerView;
    private MainMenuAdapter mExampleAdapter;
    private RequestQueue mRequestQueue;
    private GridLayoutManager gridLayoutManager;

    private List<MainMenuPojo> mExampleList = new ArrayList<>();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!isConnected(MainMenuActivity.this)) {
            Intent intent = new Intent(MainMenuActivity.this, No_Internet.class);
            startActivity(intent);
            finish(); // Yo finish garepaxi yo activity kill vayera NoInternetConnection page khulx n it is done so that when we enter in that page and when we press back this ServiceMenu Activity gets load again.
        }

        progressDialog = new ProgressDialog(MainMenuActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please Wait"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();

        gridLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRequestQueue = Volley.newRequestQueue(this);

        jsonParse();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //--------------------------------- For No Internet Connection show page -----------------------------------------------//

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return true;
        } else
            return false;
    }

// ------------------------------------------ No Internet Connection Ends here --------------------------------------------//

    /**
     *
     */
    public void jsonParse() {

        final String url = ("http://ksodari.pythonanywhere.com/api/categories");
//        final String url = ("https://xelwel.com.np/hamrosewaapp/api/get_organization_list");

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.i("response", url + "response:" + response);

                        try {

                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("categories");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                final JSONObject hit = jsonArray.getJSONObject(i);

                                Collections.sort(mExampleList, new Comparator<MainMenuPojo>() {

                                    @Override
                                    public int compare(final MainMenuPojo userInfo1, final MainMenuPojo userInfo2) {

                                        return userInfo1.getCreator().compareTo(userInfo2.getCreator());

                                    }
                                });

                                String creatorName = hit.getString("cat_name"); // yo string paxi ko obj ko name mathi extra ma haleko xa

                                String urlfront = "http://ksodari.pythonanywhere.com";
                                String imageUrlback = hit.getString("cat_icon_url");
                                String item_id = hit.getString("cat_id");

//                                String imageUrl = (urlfront + "" + imageUrlback);

                                mExampleList.add(new MainMenuPojo( creatorName, item_id)); // Yo add vaneko yesle MainMenuPojo ko getId() ko thau ma yo string ko value chae add garne ho; tya UI ma dekhako items Adapter class le gareko ho; yo add le pojo class ma value add gareko ho.
                            }

                            mExampleAdapter = new MainMenuAdapter(MainMenuActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(MainMenuActivity.this);

//                            progressbar.setVisibility(View.GONE);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error Parsing Data", Toast.LENGTH_LONG);
                            e.printStackTrace();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainMenuActivity.this, "No items available", Toast.LENGTH_SHORT).show();
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

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("api_key", "123456789");
//                return params;
//            }
        };

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(MainMenuPojo userInfo) {

        Intent detailIntent = new Intent(this, ItemsPriceActivity.class);

        detailIntent.putExtra("EXTRA", userInfo); // YO userinfo i.e. pojo class ma set vako value chae putExtra gareko ho, i.e. yo userinfo bata getId(), getCreator() k tanne tyo uta ko class ma define garne. Ya bata euta pojo class ma vako purae value send gareko ho
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

//---------------------------------------------- Searching System ----------------------------------------------------------//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {

                        //--------  Do something when collapsed  -----------//

                        mExampleAdapter.setFilter(mExampleList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) { // yo vaneko search icon ma click garda true value return garne..

                        //--------  Do something when expanded  --------------//

                        return true; // Return true to expand action view
                    }
                });

        return true; // Returning true to onCreate Option Menu
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<MainMenuPojo> filteredModelList = filter(mExampleList, newText);

        mExampleAdapter.setFilter(filteredModelList); // This setFilter is called from DepartAdapter Class
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<MainMenuPojo> filter(List<MainMenuPojo> models, String query) {
        query = query.toLowerCase();

        final List<MainMenuPojo> filteredModelList = new ArrayList<>();
        for (MainMenuPojo model : models) {
            final String text = model.getCreator().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    public void setAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Call the waiter");
        builder.setMessage("Press the below button to proceed...Our waiter will be available in few minutes");
        builder.setPositiveButton("Yes, Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//------------------------------------ Menu Items Performance Starts here ----------------------------------------------//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.MyOrder) {

            Intent intent = new Intent(MainMenuActivity.this, MyOrderActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.CallWaiter) {

            setAlertDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    //------------------------------------ Menu Items Performance Ends here ----------------------------------------------//
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent intent = new Intent(MainMenuActivity.this, MainMenuActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_myorder) {

            Intent intent = new Intent(MainMenuActivity.this, MyOrderActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addtocart) {

            Intent intent = new Intent(MainMenuActivity.this, MyCartActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_callwaiter) {

            setAlertDialog();

        }

        else if (id == R.id.aboutus) {

            Intent intent = new Intent(MainMenuActivity.this, AboutUs.class);
            startActivity(intent);


        } else if (id == R.id.feedback) {

            Intent intent = new Intent(MainMenuActivity.this, Feedback.class);
            startActivity(intent);

        } else if (id == R.id.help) {

            Intent intent = new Intent(MainMenuActivity.this, HelpingActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//        @Override
//    public void onRefresh() {
//        if (mExampleList.isEmpty()) {
//            jsonParse();
//        }
//        swipeRefreshLayout.setRefreshing(true);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        },3000);
//    }
}
