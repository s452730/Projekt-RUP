package com.example.wearapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private EditText adresM;
    private EditText adresP;
    private EditText godzina;
    private TextView txtShow;
    private Button button;
    private Button button2;
    private List<JSONObject> trace_json;


    private String homeAddress;
    private String workAddress;
    private int hourOfWorkingStart;


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


        //dla uruchomenia api tras -> new TraceData.start(), resultat w trace_json

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

    class TraceData extends Thread{

        public void getTraceByJson(String url) throws JSONException {
            //zwraca czas, kiedy trzeba wyjsc z domu i trase
            JSONObject info = null;
            info = new JSONObject(url);
            JSONObject departure_time = null;
            JSONObject travel_mode = null;
            departure_time = info.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONObject("departure_time");
            travel_mode = info.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONArray("steps")
                    .getJSONObject(1)
                    .getJSONObject("transit_details")
                    .getJSONObject("travel_mode");

            trace_json.add(departure_time);
            trace_json.add(travel_mode);
        }

        @Override
        public void run() {
            try {
                URL url = new URL("https://maps.googleapis.com/maps/api/directions" +
                        "/json?key=AIzaSyCNx1cp5ReJvuzJ5XqCBijNxy2B0mAUl_s&mode=transit&origin="+homeAddress
                        +"&destination="+workAddress
                        +"&arrival_time="+hourOfWorkingStart);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.connect();

                InputStream stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                String trace_data = buffer.toString();
                getTraceByJson(trace_data);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}