package com.example.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<model> dataholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

            recyclerView=(RecyclerView)findViewById(R.id.recview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            Cursor cursor=new dbmanager(this).readalldata();
            dataholder=new ArrayList<>();

            while(cursor.moveToNext())
            {
                model obj=new model(cursor.getString(1));
                dataholder.add(obj);
            }

            myadapter adapter=new myadapter(dataholder, getApplicationContext());
            recyclerView.setAdapter(adapter);

        }
}