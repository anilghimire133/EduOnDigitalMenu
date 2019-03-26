package com.example.ayush.digitalmenu;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayush.digitalmenu.MainMenu.MainMenuActivity;
import com.example.ayush.digitalmenu.MainMenu.MainMenuAdapter;
import com.example.ayush.digitalmenu.MainMenu.MainMenuPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OurService extends Service {

    private List<MainMenuPojo> mExampleList = new ArrayList<>();
    public RequestQueue mRequestQueue;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    public void jsonParse() {

        final String url = ("http://ksodari.pythonanywhere.com/api/categories");

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

                                String creatorName = hit.getString("cat_name"); // yo string paxi ko obj ko name mathi extra ma haleko xa

                                String urlfront = "http://ksodari.pythonanywhere.com";
                                String imageUrlback = hit.getString("cat_icon_url");
                                String id = hit.getString("cat_id");

                                String imageUrl = (urlfront + "" + imageUrlback);

                                mExampleList.add(new MainMenuPojo( creatorName, id));
                            }

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
                Toast.makeText(OurService.this, "No items available", Toast.LENGTH_SHORT).show();
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

        mRequestQueue.add(request);
    }
}
