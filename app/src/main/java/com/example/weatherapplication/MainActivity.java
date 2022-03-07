package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

//Activity represents more or less a screen within an andorid app

public class MainActivity extends AppCompatActivity {

    // Declaring request queue for volley requests
    private RequestQueue queue;
    String apiUrl =  "https://api.openweathermap.org/data/2.5/weather?q=tampere&units=metric&appid=5b1b578abf8b4ecc9b2f6e86e3b0ee17";
    String testUrl = "https://jsonplaceholder.typicode.com/users/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // On create method sets up the GUI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Make an instance of the queue
        queue = Volley.newRequestQueue(this);
    }

    public void updateWeather(View view) {
        //fetch the weather data from some weather api providing server
        // API-KEY -> https://api.openweathermap.org/data/2.5/weather?q=tampere&appid=5b1b578abf8b4ecc9b2f6e86e3b0ee17
        // to make http request we use Volley which is like fetch/axios for javascript
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                response -> {
                    // google doc link https://google.github.io/volley/simple.html here the listeners used are from old java android
                    // studio recommends lambda functions which are much shorter

                    // Toast is like a small popup floating notif
                    //Toast.makeText(this,response,Toast.LENGTH_LONG).show();
                    parseJsonAndUpdateUI(response);
                },
                volleyError -> {
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
                });

        // Sending request by adding it to queue
        queue.add(stringRequest);
    }

    private void parseJsonAndUpdateUI(String response) {

        //Random data that we will update when button update is clicked
        String weatherDescripion = "";
        double temperature =0;
        double windspeed =0;
        // To update these to the screen
        try {
            // JSON parsing it to var called weather
            JSONObject weather = new JSONObject(response);

            // giving values to vars by further parsing the receiver json object (as it has sub arrays and objects)
            // [ blah {id: "asda"}] square bracket is json Array
            // {} curl bracket is json object
            weatherDescripion = weather.getJSONArray("weather").getJSONObject(0).getString("description");
            temperature = weather.getJSONObject("main").getDouble("temp");
            windspeed = weather.getJSONObject("wind").getDouble("speed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView weatherDescriptionTextView = findViewById(R.id.textViewWeatherDesc);
        TextView temperatureTextView = findViewById(R.id.textViewTemp);
        TextView windSpeedTextView = findViewById(R.id.textViewWindSpeed);
        weatherDescriptionTextView.setText(weatherDescripion);

        temperatureTextView.setText(""+temperature+"C");

        windSpeedTextView.setText(""+windspeed+"m/s");

    }


    public void openForecast(View view) {
        //open forecast button click handler in java

        // To open other Acitivities
        // 1. Create an intent for opening ForecastActivity
        Intent intent = new Intent(this, ForecastActivity.class);
        EditText cityName = findViewById(R.id.editTextCityName);
        String intentValue = cityName.getText().toString();
        // 2. Sending some data to intent
        // put extra takes key:value pairs
        intent.putExtra("CITY_NAME",intentValue);
        // 3. Start activity through intent
        startActivity(intent);
    }
}