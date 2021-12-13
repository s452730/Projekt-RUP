package com.example.wearapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import ClothingService.TempEnum;
import ClothingService.ClothingService;

public class MainActivity extends AppCompatActivity {


    private EditText adresM;
    private EditText adresP;
    private EditText godzina;
    private TextView txtShow;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adresM = (EditText) findViewById(R.id.adresM);
        adresP = (EditText) findViewById(R.id.adresP);
        godzina = (EditText) findViewById(R.id.godzina);
        txtShow = (TextView) findViewById(R.id.textTest);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);


        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
            String myTxt = adresM.getText().toString() +"\n" + adresP.getText().toString() + "\n" + godzina.getText().toString() + "\n";
                try {
                    FileOutputStream fileOut = openFileOutput("test.json",MODE_PRIVATE);
                    fileOut.write(myTxt.getBytes(StandardCharsets.UTF_8));
                    fileOut.close();
                    adresM.setText("");
                    adresP.setText("");
                    godzina.setText("");
                    Toast.makeText(MainActivity.this,"dane zapisane!",Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fileIn = openFileInput("test.json");
                    InputStreamReader reader =  new InputStreamReader(fileIn);
                    BufferedReader bufferread = new BufferedReader(reader);
                    StringBuffer strbuff = new StringBuffer();
                    String str;
                    while ((str=bufferread.readLine()) != null){
                        strbuff.append(str + "\n");
                    }
                    txtShow.setText(strbuff.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}