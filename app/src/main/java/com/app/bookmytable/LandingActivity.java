package com.app.bookmytable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.app.bookmytable.model.Restaurant;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class LandingActivity extends ActionBarActivity {

    ListView listView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.restroList);

        context = this;
        String url = "http://10.0.2.2:8080/restrobooking/resources/restaurant/find-all";

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Formulate the request and handle the response.
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Success", response.toString());
                List<Restaurant> restaurants = Restaurant.fromJson(response);
                if (restaurants != null) {
                    List<String> restoNames = new ArrayList<>();
                    for (Restaurant restaurant : restaurants) {
                        restoNames.add(restaurant.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LandingActivity.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, restoNames);

                    // Assign adapter to ListView
                    listView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.getMessage());

            }
        });

        // Add the request to the RequestQueue.
        mRequestQueue.add(jsObjRequest);



        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item value
                String  itemValue = (String) listView.getItemAtPosition(position);

                Intent intent = new Intent(LandingActivity.this, RestroDetailActivity.class);
                intent.putExtra("HotelId", itemValue);
                startActivity(intent);
            }

        });
    }

}