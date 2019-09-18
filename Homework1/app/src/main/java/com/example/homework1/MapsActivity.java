package com.example.homework1;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String lat;
    private String lng;
    private String temp;
    private String humid;
    private String wind;
    private String precip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        temp = getIntent().getStringExtra("temp");
        humid = getIntent().getStringExtra("humid");
        wind = getIntent().getStringExtra("wind");
        precip = getIntent().getStringExtra("precip");

        System.out.println("lat: " + lat);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("Hello map is ready");
        mMap = googleMap;
        double numLat = Double.parseDouble(lat);
        double numLng = Double.parseDouble(lng);
        // Add a marker in Sydney and move the camera
        LatLng loc = new LatLng(numLat, numLng);
        mMap.addMarker(new MarkerOptions().position(loc).title("Marked Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
        TextView tempView = (TextView) findViewById(R.id.tempNum);
        tempView.setText(temp + "°");
        TextView tempView2 = (TextView) findViewById(R.id.humidNum);
        tempView2.setText(humid);
        TextView tempView3 = (TextView) findViewById(R.id.windNum);
        tempView3.setText(wind + " mph");
        TextView tempView4 = (TextView) findViewById(R.id.precipNum);
        tempView4.setText(precip + "%");
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
}
