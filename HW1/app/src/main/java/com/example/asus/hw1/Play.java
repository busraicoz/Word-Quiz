package com.example.asus.hw1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Created by Asus on 5.04.2017.
 */

public class Play extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Button back,add_word,pause;
    TextView score,hs;
    private final int MULTIPLE_CHOICE_COUNT =5;
    ArrayList<String> array;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private HashMap<String, String> dict;
    private ArrayList<String> definitions;
    ArrayList wordarrray=new ArrayList();
    private MediaPlayer mp,fail;
    private float points;
    private float highScore;
    private String currentWord ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        mp = MediaPlayer.create(this, R.raw.music);
        mp.start();
        dict = new HashMap<>();
        readAll();
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        back = (Button)findViewById(R.id.button6);
        add_word = (Button)findViewById(R.id.button12);
        pause= (Button)findViewById(R.id.pause);
        score = (TextView) findViewById(R.id.score);
        hs=(TextView)findViewById(R.id.hs);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(Play.this, MainActivity.class);
                startActivity(intocan);
            }
        });
        add_word.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(Play.this, AddNew.class);
                startActivity(intocan);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               onPause();
            }
        });
        generateRandom();
        SharedPreferences prefs = getSharedPreferences("myprefs", MODE_PRIVATE);
        highScore = prefs.getFloat("highScore", /* default */ 0);
        hs.setText("highScore:"+highScore);

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mp != null) {
            mp.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mp != null) {
            mp.start();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView the_word = (TextView) findViewById(R.id.the_word);
        super.onSaveInstanceState(outState);
        outState.putFloat("points", points);
        String word=the_word.getText().toString();
        outState.putString("the_word",word);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        points = savedInstanceState.getFloat("points", /* default */ 0);
        score.setText("score:"+points);
    }


    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {

        if(dict.get(currentWord).equals(list.getItemAtPosition(index).toString())){
            points++;
            score.setText("score:"+points);
            if (points > highScore) {
                highScore = points;
                hs.setText("highScore:"+highScore);
                SharedPreferences prefs = getSharedPreferences(
                        "myprefs", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                prefsEditor.putFloat("highScore", highScore);
                prefsEditor.apply();

                Toast.makeText(this, ("New High Score = " + highScore),Toast.LENGTH_SHORT).show();

            }
            Toast.makeText(this, ("Congratulations! Score = " + points + ", highscore=" + highScore),Toast.LENGTH_SHORT).show();}
        else{
            points= (float) (points-0.5);
            score.setText("score:"+points);
            Toast.makeText(this, (":-( Mistake. Score = " + points + ", hi=" + highScore),Toast.LENGTH_SHORT).show();

        }

        if(points<0){
            mp.pause();
            fail= MediaPlayer.create(this, R.raw.fail);
            fail.start();

            AlertDialog.Builder altdial = new AlertDialog.Builder(Play.this);
            altdial.setMessage("You need more study:( Would you like to study more? ").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intocan = new Intent(Play.this, Try.class);
                            startActivity(intocan);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intocan = new Intent(Play.this, MainActivity.class);
                            startActivity(intocan);
                        }
                    });

            AlertDialog alert = altdial.create();
            alert.show();

        }
        generateRandom();

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
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] parts = line.split("\t");
            dict.put(parts[0], parts[1]);

        }
             definitions = new ArrayList<>();
             array = new ArrayList<>(dict.keySet());

    }

  private void generateRandom() {

      Collections.shuffle(array);
      String word = array.get(0);
         if (!wordarrray.contains(word)) {
              wordarrray.add(word);
              //ask question
              TextView the_word = (TextView) findViewById(R.id.the_word);
              the_word.setText(word);
              currentWord = word;
              definitions.clear();
              for (int i = 0; i < MULTIPLE_CHOICE_COUNT; i++) {
                  definitions.add(dict.get(array.get(i)));
              }
              Collections.shuffle(definitions);
              adapter = new ArrayAdapter<String>(
                      this,
                      R.layout.list_layout,
                      R.id.content,
                      definitions
              );

              list.setAdapter(adapter);
      }
        else if (wordarrray.contains(word)) {
              generateRandom();

              }
      if (wordarrray.size()==array.size()) {
          AlertDialog.Builder altdial = new AlertDialog.Builder(Play.this);
          altdial.setMessage("Game is over, \n" +
                  "New words will be added soon:)Do you want to play again? ").setCancelable(false)
                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          Intent intocan = new Intent(Play.this, Play.class);
                          startActivity(intocan);
                      }
                  })
                  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          Intent intocan = new Intent(Play.this, MainActivity.class);
                          startActivity(intocan);
                      }
                  });

          AlertDialog alert = altdial.create();
          alert.show();

      }





  }}