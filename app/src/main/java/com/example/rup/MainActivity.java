package com.example.rup;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.rup.API.WeatherAPI;

public class MainActivity extends AppCompatActivity {

    private Button mainbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainbtn = findViewById(R.id.mainbtn);

        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //OPTIONS
                String city = "Poznan";
                String date = "2021-12-09";
                // String hour = "0";
                String key = "992e7cab06164165980213921210812";
                String url = "https://api.weatherapi.com/v1/history.json?key=" + key +
                        "&q=" + city + "&dt=" + date;
                new WeatherAPI().execute(url);
            }
        });
    }
}