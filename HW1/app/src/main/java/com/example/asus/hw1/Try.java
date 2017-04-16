package com.example.asus.hw1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Asus on 5.04.2017.
 */

public class Try extends AppCompatActivity{
    TextView word,mean;
    Button play,add;
    private final int MULTIPLE_CHOICE_COUNT =5;
    ArrayList<String> array;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private HashMap<String, String> dict;
    private String currentWord ="";
    private ArrayList<String> definitions;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train);
        word= (TextView) findViewById(R.id.wordt);
        mean = (TextView) findViewById(R.id.meant);
        play=(Button)findViewById(R.id.pn);
        add=(Button)findViewById(R.id.an);

        word.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             readAll();
            }
        });
        mean.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                readAll();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(Try.this, Play.class);
                startActivity(intocan);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(Try.this, AddNew.class);
                startActivity(intocan);
            }
        });

        dict = new HashMap<>();
        readAll();
    }

    private void readAll() {
        Scanner scan = new Scanner(
                getResources().openRawResource(R.raw.dictionary));
        readFileHelper(scan);

        try {
            Scanner scan2 = new Scanner(openFileInput("new_words.txt"));
            readFileHelper(scan2);
        } catch (Exception e) {
        }
    }

    private void readFileHelper(Scanner scan) {
        TextView the_word = (TextView) findViewById(R.id.wordt);
        TextView the_mean = (TextView) findViewById(R.id.meant);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] words = line.split("\t");
            dict.put(words[0], words[1]);
            the_word.setText(words[0]);
            the_mean.setText(words[1]);
            definitions = new ArrayList<>();
            array = new ArrayList<>(dict.keySet());
            Collections.shuffle(array);
            String word = array.get(0);
            String meant =dict.get(array.get(0));
            //ask question
            TextView w= (TextView) findViewById(R.id.wordt);
            w.setText(word);
            TextView m= (TextView) findViewById(R.id.meant);
            m.setText(meant);
        }
        scan.close();
    }
}


