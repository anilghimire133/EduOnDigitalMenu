package com.example.ayush.digitalmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    TextView textView, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        textView = findViewById(R.id.description_paragraph2);
        textView2 = findViewById(R.id.web_page);

//        String text = new String("");
//        String text2 = new String("www.grovetta.com");
//        textView.setText(text);
//        textView2.setText(text2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("About Us");
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
