package com.app.bookmytable.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.app.bookmytable.RestroDetailActivity;
import com.app.bookmytable.model.Restaurant;

import org.json.JSONArray;

import java.util.List;
public class RestroListFragment extends ListFragment {

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);


        context = getActivity();
        String url = "http://10.0.2.2:8080/restrobooking/resources/restaurant/find-all";

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

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
                    setListAdapter(new RestroAdapter(getActivity(), restaurants));
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

    return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // ListView Clicked item value
        Restaurant  selRestro = (Restaurant) l.getItemAtPosition(position);

        Intent intent = new Intent(getActivity(), RestroDetailActivity.class);
        intent.putExtra("HotelId", selRestro.getId());
        startActivity(intent);
    }
}
