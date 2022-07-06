package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.etName);
        tv = findViewById(R.id.tvDisplay);
        bt = findViewById(R.id.btGet);
    }

    public void get(View v){
        String apiKey = "43b285218214a203a27d0113064d4d8b";
        String city = et.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=43b285218214a203a27d0113064d4d8b";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject("main");
                    String temperature = object.getString("temp");
                    Double temp = Double.parseDouble(temperature) - 273.15;
                    String feels = object.getString("feels_like");
                    Double feel = Double.parseDouble(feels) -273.15;
                    String min = object.getString("temp_min");
                    Double minTemp = Double.parseDouble(min) - 273.15;
                    String max = object.getString("temp_max");
                    Double maxTemp = Double.parseDouble(max) - 273.15;
                    String humidity = object.getString("humidity");
                    tv.setText("Temp = " + temp.toString().substring(0,5) + " C"
                            + "\nFeels Like = " + feel.toString().substring(0,5) + " C"
                            + "\nMinimun Temperature = " + minTemp.toString().substring(0,5)+ " C"
                            + "\nMaximum Temperature = " + maxTemp.toString().substring(0,5)+ " C"
                            + "\nHumidity = " + humidity);



                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}