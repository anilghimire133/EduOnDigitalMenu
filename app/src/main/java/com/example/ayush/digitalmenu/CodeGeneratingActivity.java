package com.example.ayush.digitalmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayush.digitalmenu.MainMenu.MainMenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodeGeneratingActivity extends AppCompatActivity {

    ImageView imageView;
    EditText editText;
    TextView textView;
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_generating);

        Intent intent = getIntent();
        // gets user_token or username of newly created user from api call
        final String user_token = intent.getStringExtra("USER_TOKEN");

        imageView = findViewById(R.id.goarrow);
        editText = findViewById(R.id.edttxt);
        textView = findViewById(R.id.textView2);

        Spanned title = Html.fromHtml("<font> Insert code </font> <font color='black'> <b>"+ user_token +"</b> </font> <font> to start your order from </font> <font color='#00b187'> <b> Digital Menu</font> </b>");

        //                        editText.setText(String.format("my name is %s . i have Rs%s","xya",535));
        textView.setText(title);
//        textView.setText(user_token);
//        textView.setText("#S8690");   // user_token send to display view

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText.setBackgroundResource(R.drawable.edittextselector);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check form validation for empty case
                if (editText.getText().toString().equals("")) {
                    editText.setError(Html.fromHtml("<font color='red'>Please enter the below code</font>"));
                    editText.setBackgroundResource(R.drawable.error_edittext_selector);
                    mRequestQueue = Volley.newRequestQueue(CodeGeneratingActivity.this);
                } else {
                    // else run jsonParse normally when user has entered user_token
                    jsonParse();
                }
            }
        });
        mRequestQueue = Volley.newRequestQueue(this);
    }

    public void jsonParse() {

        final String url = "http://ksodari.pythonanywhere.com/api/request/token/login";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.i("response", url + "response:" + response);

                try {
                    JSONObject object = new JSONObject(response);

//                    String token = object.getString("token");
//                    String user = object.getString("user_token");
                    String message = object.getString("message");
                    String code = object.getString("code");

//------------------------- Yo code token bata ayeko code saga match garauna; so y request accept vayo vaner use gareko -----------------------//

                    if (code.equals("0002")) {
                        Intent intent = new Intent(CodeGeneratingActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                    } else {
                        editText.setError(Html.fromHtml("<font color='red'>" + message + "</font>"));
                        editText.setBackgroundResource(R.drawable.error_edittext_selector);
                        Toast.makeText(CodeGeneratingActivity.this, "Error Authentication!!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                editText.setError(Html.fromHtml("<font color='red'>Please check the token</font>"));
                editText.setBackgroundResource(R.drawable.error_edittext_selector);
                error.printStackTrace();
                Toast.makeText(CodeGeneratingActivity.this, "Error user authentication", Toast.LENGTH_SHORT).show();
            }
        }) {
            // Initializing intent object to get data from previous request
            Intent intent = getIntent();
            final String token = intent.getStringExtra("AUTH_TOKEN");   // gets token value from Starting_LoginActivity
            final String usertoken = editText.getText().toString(); // gets user_token value from input form

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                String token = "e973419cf2d6da5660f8a42f0d6b73ca87f14780";
                String auth_token = "Token " + token;   // "Token e973419cf2d6da5660f8a42f0d6b73ca87f14780"
                params.put("Authorization", auth_token);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_token", usertoken);        // sends user_token for login check on body
                return params;
            }
        };
        mRequestQueue.add(request);
    }
}
