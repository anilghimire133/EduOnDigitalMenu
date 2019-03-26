package com.example.ayush.digitalmenu.StartingLogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayush.digitalmenu.CodeGeneratingActivity;
import com.example.ayush.digitalmenu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Starting_LoginActivity extends AppCompatActivity {

//    public static final String USER_TOKEN = "user";

    Button button;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.button);

      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

//              Intent intent = new Intent(Starting_LoginActivity.this, CodeGeneratingActivity.class);
//              startActivity(intent);
              jsonParse();
          }
      });

        mRequestQueue = Volley.newRequestQueue(this);

    }
    public void jsonParse(){

        final String url = "http://ksodari.pythonanywhere.com/api/request/token";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.i("response", url + "response:" + response);

                try{
                    JSONObject object = new JSONObject(response);

                    String token = object.getString("token");
                    String user = object.getString("user_token");
                    String code = object.getString("code");

//------------------------- Yo code token bata ayeko code saga match garauna; so y request accept vayo vaner use gareko -----------------------//

                    UserInfo info = new UserInfo();

                    if (code.equals("0002")){
                        Intent intent = new Intent(Starting_LoginActivity.this, CodeGeneratingActivity.class);
                        intent.putExtra("USER_TOKEN", user);
                        intent.putExtra("AUTH_TOKEN", token);
                        startActivity(intent);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(request);
    }
}
