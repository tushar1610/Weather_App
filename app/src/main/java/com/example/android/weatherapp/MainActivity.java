package com.example.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.weatherapp.data.MyDbHelper;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextInputLayout city_name;
    Button enter_button, set_fav_btn;
    TextView text_temp;
    String API_KEY = "82bd59182a525cf51dfb3987fce87115";
    String city;
    MyDbHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city_name = findViewById(R.id.edit_text_city);
        enter_button =findViewById(R.id.enter_btn);
        set_fav_btn = findViewById(R.id.fav_btn);
        text_temp = findViewById(R.id.temp);

        myDbHelper = new MyDbHelper(MainActivity.this);

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = city_name.getEditText().getText().toString().trim();
                String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+API_KEY;
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Temperature", String.valueOf(url));
                            JSONObject object1 = response.getJSONObject("main");
                            String temperature = object1.getString("temp");
                            text_temp.setText(city + ": " + temperature);
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Please check the city name", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);
            }
        });

        set_fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                processinsert(city);
                Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void processinsert(String cityName)
    {
        myDbHelper.addCity(cityName);
        Toast.makeText(this, "Added to favourite", Toast.LENGTH_SHORT).show();
    }
}