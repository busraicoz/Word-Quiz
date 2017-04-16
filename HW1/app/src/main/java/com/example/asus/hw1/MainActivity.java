package com.example.asus.hw1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Asus on 5.04.2017.
 */

public class MainActivity extends AppCompatActivity {
    Button btntry;
    Button btnadd;
    Button btnplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btntry = (Button)findViewById(R.id.btn1);
        btnplay = (Button)findViewById(R.id.button4);
        btnadd = (Button)findViewById(R.id.button3);
        btntry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(MainActivity.this, Try.class);
                startActivity(intocan);
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(MainActivity.this, AddNew.class);
                startActivity(intocan);
            }
        });
        btnplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(MainActivity.this,Play.class);
                startActivity(intocan);
            }
        });



    }
}