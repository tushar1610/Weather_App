package com.example.android.weatherapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android.weatherapp.model.Weather;
import com.example.android.weatherapp.params.Params;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(@Nullable Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Params.KEY_CITY_NAME + " TEXT " + ")";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCity(String cityName){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_CITY_NAME, cityName);
        database.insert(Params.TABLE_NAME,null, values );
        database.close();
    }

    public int deleteCity(int position){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(Params.TABLE_NAME, String.valueOf(Params.KEY_ID) + "=?", new String[]{String.valueOf(position)});
    }

    public int getCount(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Params.TABLE_NAME, null);
        int count = cursor.getCount();
        return count;
    }

    public ArrayList<Weather> getAllCity(){
        ArrayList<Weather> favouriteLsit = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;

        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()){
            do{
                Weather favourite = new Weather();
                favourite.setId(Integer.parseInt(cursor.getString(0)));
                favourite.setCity_name(cursor.getString(1));
                favouriteLsit.add(favourite);
            }while (cursor.moveToNext());
        }

        return favouriteLsit;
    }
}
