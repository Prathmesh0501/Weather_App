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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextInputEditText et;
    TextView tv,t,h,maxT,ps,minT,storm,rise,set;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.etName);
        tv = findViewById(R.id.tvDisplay);
        bt = findViewById(R.id.btGet);
        t = findViewById(R.id.temp);
        h = findViewById(R.id.humidity);
        maxT = findViewById(R.id.maxTemp);
        ps = findViewById(R.id.pressure);
        minT = findViewById(R.id.minTemp);
        storm = findViewById(R.id.wind);
        rise = findViewById(R.id.sunrise);
        set = findViewById(R.id.sunset);

    }

    public void get(View v){
        String apiKey = "43b285218214a203a27d0113064d4d8b";
        String city = et.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=43b285218214a203a27d0113064d4d8b&units=metric";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject("main");
                    JSONObject object1 = response.getJSONObject("sys");
                    JSONObject object2 = response.getJSONObject("wind");




                    String temperature = object.getString("temp");
                    Double temp = Double.parseDouble(temperature);

                    String feels = object.getString("feels_like");
                    String min = object.getString("temp_min");
                    Double minTemp = Double.parseDouble(min);
                    String max = object.getString("temp_max");
                    Double maxTemp = Double.parseDouble(max);
                    String humidity = object.getString("humidity");
                    String pressure = object.getString("pressure");
                    String wind = object2.getString("speed");

                    long sunRise = object1.getLong("sunrise");
                    long javaTime = sunRise * 1000L; //converting unix timestamp into java timestamp.
                    Date date = new Date(javaTime);
                    String Sunrise = new SimpleDateFormat("hh:mm").format(date);

                    long sunSet = object1.getLong("sunset");
                    long JavaTime = sunSet *1000L;
                    Date date1 = new Date(JavaTime);
                    String Sunset = new SimpleDateFormat("HH:mm").format(date1);

                    t.setText(Math.round(temp) + "°C");
                    minT.setText( Math.round(minTemp)+ "°C");
                    maxT.setText(Math.round(maxTemp) +"°C");
                    h.setText(humidity + " %");
                    ps.setText(pressure +" hPa");
                    rise.setText(Sunrise);
                    set.setText(Sunset);
                    storm.setText(wind + " m/s");





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