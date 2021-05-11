package com.example.android.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.weatherapp.data.MyDbHelper;
import com.example.android.weatherapp.model.Weather;

import java.util.ArrayList;

public class MyFavouriteAdapter extends RecyclerView.Adapter<MyFavouriteAdapter.MyFavouriteViewholder> {

    Context context;
    MyDbHelper myDbHelper;
    ArrayList<Weather> weathers;

    public MyFavouriteAdapter(Context context, MyDbHelper myDbHelper) {
        this.context = context;
        this.myDbHelper = myDbHelper;
    }

    @NonNull
    @Override
    public MyFavouriteViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.singlerow, parent, false);
        return new MyFavouriteViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFavouriteAdapter.MyFavouriteViewholder holder, int position) {
        weathers = myDbHelper.getAllCity();
        holder.cityName.setText(weathers.get(position).getCity_name());
        holder.deleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deleteConst = myDbHelper.deleteCity(position);
                weathers.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDbHelper.getCount();
    }

    public class MyFavouriteViewholder extends RecyclerView.ViewHolder{

        TextView cityName;
        ImageButton deleteCity;

        public MyFavouriteViewholder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.displayname);
            deleteCity = itemView.findViewById(R.id.delete_record);
        }
    }
}
