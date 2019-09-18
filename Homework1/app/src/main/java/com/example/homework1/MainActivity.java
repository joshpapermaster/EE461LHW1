package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request.Method;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private TextView errorText;
    private Intent weatherIntent;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorText = (TextView) findViewById(R.id.errorText);
    }

    public void showWeatherPage(View view) {
        weatherIntent = new Intent(this, MapsActivity.class);
        TextView addressText = (TextView) findViewById(R.id.addressText);
        String address = addressText.getText().toString();
        String formattedAddress = formatAddress(address);
        String googleReq = "https://maps.googleapis.com/maps/api/geocode/json?address=" + formattedAddress + "&key=AIzaSyAcCfwLekUhJ8VcwsHe5Qcj17wDpk7zw0A";
        String data = "";
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                googleReq,
                null,
                googleSuccessListener(),
                googleErrorListener());
        queue.add(jsonReq);
    }

    private Response.Listener<JSONObject> googleSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String lat = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
                    String lng = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
                    weatherIntent.putExtra("lat", lat);
                    weatherIntent.putExtra("lng", lng);
                    String darkSkyReq = "https://api.darksky.net/forecast/d075ad88712cca7e99a699c4e5a700c3/" + lat + "," + lng;
                    JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                            darkSkyReq,
                            null,
                            darkSuccessListener(),
                            darkErrorListener());
                    queue.add(jsonReq);
                } catch (Exception e) {
                    System.out.println(e);
                    errorText.setText("Error, please try again");
                }
            }
        };
    }

    private Response.ErrorListener googleErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                errorText.setText("Error, please try again");
            }
        };
    }

    private Response.Listener<JSONObject> darkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                try{
                    JSONObject current = response.getJSONObject("currently");
                    String temp = current.getString("temperature");
                    String humid = current.getString("humidity");
                    String wind = current.getString("windSpeed");
                    String precip = current.getString("precipProbability");

                    weatherIntent.putExtra("temp", temp);
                    weatherIntent.putExtra("humid", humid);
                    weatherIntent.putExtra("wind", wind);
                    weatherIntent.putExtra("precip", precip);
                    startActivity(weatherIntent);
                }
                catch (Exception e){
                    System.out.println(e);
                    errorText.setText("Error, please try again");
                }

            }
        };
    }

    private Response.ErrorListener darkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                errorText.setText("Error, please try again");
            }
        };
    }

    private String formatAddress(String address) {
        String[] splitAddress = address.split(" ");
        String result = "";
        for (int i=0; i<splitAddress.length; i++) {
            if (i<splitAddress.length - 1)
                result += splitAddress[i] + "+";
            else
                result += splitAddress[i];
        }
        return result;
    }

}
