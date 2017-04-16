package com.example.asus.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.asus.hw1.R.raw.dictionary;


/**
 * Created by Asus on 6.04.2017.
 */

public class AddNew extends AppCompatActivity {
    Button back, add;
    EditText edt1, edt2;
    ArrayList<String> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        add = (Button) findViewById(R.id.button);
        back = (Button) findViewById(R.id.button2);
        edt1 = (EditText) findViewById(R.id.editText3);
        edt2 = (EditText) findViewById(R.id.editText4);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(AddNew.this, MainActivity.class);
                startActivity(intocan);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eng = edt1.getText().toString();
                String tr = edt2.getText().toString();
                if (v.getId() == add.getId()) {
                    if (eng.length() == 0) {
                        edt1.requestFocus();
                        edt1.setError("This field cannot be empty!");
                    } else if (!eng.matches("[a-zA-Z ]+")) {
                        edt1.requestFocus();
                        edt1.setError("Only english alphabetical characters can be entered!");
                    } else if (tr.length() == 0) {
                        edt2.requestFocus();
                        edt2.setError("This field cannot be empty!");
                    } else if (!tr.matches("[a-zA-Z ]+")) {
                        edt2.requestFocus();
                        edt2.setError("Only alphabetical characters can be entered!");
                    } else {

                        try {
                            PrintStream output = new PrintStream(openFileOutput("new_words.txt", MODE_APPEND));
                            Scanner scanner = new Scanner(getResources().openRawResource(dictionary));
                            while(scanner.hasNext()){
                                String line = scanner.nextLine();
                                String[] words = line.split("\t");
                                arrayList.add(words[0]);
                            }
                            Scanner scan = new Scanner(openFileInput("new_words.txt"));
                            while(scan.hasNext()){
                                String line = scan.nextLine();
                                String[] words = line.split("\t");
                                arrayList.add(words[0]);
                            }
                            if (!arrayList.contains(eng)){
                                output.println(eng + "\t" + tr);
                                output.close();
                                Toast.makeText(getApplicationContext(), "new word added successfully", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "This word already exist!", Toast.LENGTH_LONG).show();

                            }catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        }

                    }
                }

        });
    }
   }