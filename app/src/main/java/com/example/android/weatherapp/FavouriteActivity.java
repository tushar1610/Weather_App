package com.example.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.example.android.weatherapp.data.MyDbHelper;
import com.example.android.weatherapp.model.Weather;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyFavouriteAdapter myFavouriteAdapter;
    MyDbHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        myDbHelper = new MyDbHelper(FavouriteActivity.this);
        recyclerView=(RecyclerView)findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myFavouriteAdapter = new MyFavouriteAdapter(FavouriteActivity.this, myDbHelper, myDbHelper.getAllCity());
        recyclerView.setAdapter(myFavouriteAdapter);

        }
}