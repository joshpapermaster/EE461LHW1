package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        doTemp();
    }

    public void doTemp() {

        TextView temp = (TextView) findViewById(R.id.temp);
        String lat = getIntent().getStringExtra("lat");
        String lng = getIntent().getStringExtra("lng");
        temp.setText("lat: " + lat + " lng: " + lng);
    }
}
