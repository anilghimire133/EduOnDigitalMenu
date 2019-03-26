package com.example.ayush.digitalmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ayush.digitalmenu.MainMenu.MainMenuActivity;

public class Feedback extends AppCompatActivity {

    RadioButton button1, button2, button3;
    Button button4;
    ImageView imageView1, imageView2, imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.submit);

        imageView1 = findViewById(R.id.arrow1);
        imageView2 = findViewById(R.id.arrow2);
        imageView3 = findViewById(R.id.arrow3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Feedback");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button1.setBackgroundResource(R.drawable.feedback_btnselector);
                button1.setTextColor(Color.WHITE);
                imageView1.setColorFilter(Color.argb(255, 255, 255, 255));

                button2.setBackgroundResource(R.drawable.default_selector);
                button2.setTextColor(Color.BLACK);
                imageView2.setColorFilter(Color.argb(255, 0, 177, 135));

                button3.setBackgroundResource(R.drawable.default_selector);
                button3.setTextColor(Color.BLACK);
                imageView3.setColorFilter(Color.argb(255, 0, 177, 135));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button2.setBackgroundResource(R.drawable.feedback_btnselector);
                button2.setTextColor(Color.WHITE);
                imageView2.setColorFilter(Color.argb(255, 255, 255, 255));

                button1.setBackgroundResource(R.drawable.default_selector);
                button1.setTextColor(Color.BLACK);
                imageView1.setColorFilter(Color.argb(255, 0, 177, 135));

                button3.setBackgroundResource(R.drawable.default_selector);
                button3.setTextColor(Color.BLACK);
                imageView3.setColorFilter(Color.argb(255, 0, 177, 135));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button3.setBackgroundResource(R.drawable.feedback_btnselector);
                button3.setTextColor(Color.WHITE);
                imageView3.setColorFilter(Color.argb(255, 255, 255, 255));

                button1.setBackgroundResource(R.drawable.default_selector);
                button1.setTextColor(Color.BLACK);
                imageView1.setColorFilter(Color.argb(255, 0, 177, 135));

                button2.setBackgroundResource(R.drawable.default_selector);
                button2.setTextColor(Color.BLACK);
                imageView2.setColorFilter(Color.argb(255, 0, 177, 135));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ImageView image = new ImageView(Feedback.this);
//                image.setImageResource(R.mipmap.happy);

                TextView textView = new TextView(Feedback.this);
                textView.setText("Thank you for your feedback");
                textView.setTextSize(20);
                textView.setPadding(50,50,50,50);
                textView.setGravity(Gravity.CENTER);

                AlertDialog.Builder builder = new AlertDialog.Builder(Feedback.this);
                builder.setCancelable(false);
                builder.setTitle("Success");
                builder.setIcon(R.drawable.tick_inside_circle);
//                builder.setMessage("Thank you for your feedback");


                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Feedback.this, MainMenuActivity.class);
                        startActivity(intent);
                    }
                })
                .setView(textView);
                builder.show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
