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
import java.util.List;

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

    public boolean deleteCity(Weather cityData){
        SQLiteDatabase database = this.getWritableDatabase();
        //return database.delete(Params.TABLE_NAME, String.valueOf(Params.KEY_ID) + "=?", new String[]{String.valueOf(position)});
        String queryString = "DELETE FROM " + Params.TABLE_NAME + " WHERE " + Params.KEY_ID + "=" + cityData.getId();
        Cursor cursor = database.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        } else {
            return false;
        }
    }

    public int getCount(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Params.TABLE_NAME, null);
        int count = cursor.getCount();
        return count;
    }

    public List<Weather> getAllCity(){
        List<Weather> favouriteList = new ArrayList<Weather>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;

        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()){
            do{
                Weather favourite = new Weather();
                favourite.setId(Integer.parseInt(cursor.getString(0)));
                favourite.setCity_name(cursor.getString(1));
                favouriteList.add(favourite);
            }while (cursor.moveToNext());
        }

        return favouriteList;
    }
}
