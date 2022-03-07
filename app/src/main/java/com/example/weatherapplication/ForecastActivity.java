package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class ForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // Check if there is any data coming from intent. If yes print that

        String cityName = getIntent().getStringExtra("CITY_NAME");
        // Lets also print it

        TextView weatherForecastCityName = findViewById(R.id.textViewForecastCityName);
        if (TextUtils.isEmpty(cityName)){ // Default value
            weatherForecastCityName.setText("Helsinki");
        }
        else // Read value
        {
            weatherForecastCityName.setText(cityName);
        }
    }
}