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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorText = (TextView) findViewById(R.id.errorText);
    }

    public void showWeatherPage(View view) {
        weatherIntent = new Intent(this, SecondActivity.class);
        TextView addressText = (TextView) findViewById(R.id.addressText);
        String address = addressText.getText().toString();
        String formattedAddress = formatAddress(address);
        String googleReq = "https://maps.googleapis.com/maps/api/geocode/json?address=" + formattedAddress + "&key=AIzaSyAcCfwLekUhJ8VcwsHe5Qcj17wDpk7zw0A";
        String data = "";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest myReq = new JsonObjectRequest(Method.GET,
                googleReq,
                null,
                createSuccessListener(),
                createErrorListener());

        queue.add(myReq);
    }

    private Response.Listener<JSONObject> createSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    weatherIntent.putExtra("lat", response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                    weatherIntent.putExtra("lng", response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                    startActivity(weatherIntent);
                } catch (Exception e) {
                    System.out.println(e);
                    errorText.setText("Error, please try again");
                }
            }
        };
    }

    private Response.ErrorListener createErrorListener() {
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
