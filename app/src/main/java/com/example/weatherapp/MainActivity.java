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

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText et;
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
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=43b285218214a203a27d0113064d4d8b";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject("main");
                    JSONObject object1 = response.getJSONObject("sys");
                    JSONObject object2 = response.getJSONObject("wind");

                    String temperature = object.getString("temp");
                    Double temp = Double.parseDouble(temperature) - 273.15;
                    String feels = object.getString("feels_like");
                    Double feel = Double.parseDouble(feels) -273.15;
                    String min = object.getString("temp_min");
                    Double minTemp = Double.parseDouble(min) - 273.15;
                    String max = object.getString("temp_max");
                    Double maxTemp = Double.parseDouble(max) - 273.15;
                    String humidity = object.getString("humidity");
                    String pressure = object.getString("pressure");
                    Double pre = Double.parseDouble(pressure) *0.000987;
                    String wind = object2.getString("speed");
                    Double speed = Double.parseDouble(wind) * 3.6;

                    long sunRise = object1.getLong("sunrise");
                    long javaTime = sunRise * 1000L; //converting unix timestamp into java timestamp.
                    Date date = new Date(javaTime);
                    String Sunrise = new SimpleDateFormat("hh:mm").format(date);

                    long sunSet = object1.getLong("sunset");
                    long JavaTime = sunSet *1000L;
                    Date date1 = new Date(JavaTime);
                    String Sunset = new SimpleDateFormat("HH:mm").format(date1);

                    t.setText(temp.toString().substring(0,2) + "°C");
                    minT.setText(minTemp.toString().substring(0,2) + "°C");
                    maxT.setText(maxTemp.toString().substring(0,2) + "°C");
                    h.setText(humidity + " %");
                    ps.setText(pre.toString().substring(0,2));
                    rise.setText(Sunrise);
                    set.setText(Sunset);
                    storm.setText(speed.toString().substring(0,2) + " km/h");

                    tv.setText("Temp = " + temp.toString().substring(0,5) + " C"
                            + "\nFeels Like = " + feel.toString().substring(0,5) + " C"
                            + "\nMinimun Temperature = " + minTemp.toString().substring(0,5)+ " C"
                            + "\nMaximum Temperature = " + maxTemp.toString().substring(0,5)+ " C"
                            + "\nHumidity = " + humidity
                            + "\n sunrise = " +Sunrise);



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