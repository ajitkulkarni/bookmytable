package com.app.bookmytable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.bookmytable.model.Restaurant;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RestroDetailActivity extends ActionBarActivity {

    Button button;
    EditText name;
    EditText email;
    EditText contactNo;
    EditText numberOfSeats;
    TextView restroNameTextView;
    String restroId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restro_detail);
        // Get the message from the intent
        Intent intent = getIntent();
        restroId = intent.getStringExtra("HotelId");

        Restaurant restroInfo = getRestaurant(restroId);

        // Create the text view
        restroNameTextView =  (TextView) findViewById(R.id.restro_name_dtl);
        TextView restroAddressTextView = (TextView) findViewById(R.id.restro_address_dtl);
        TextView capacityTextView= (TextView) findViewById(R.id.restro_capacity_dtl);

        findAllViewsId();

        //set text
        restroNameTextView.setText(restroInfo.getName());
        restroAddressTextView.setText(restroInfo.getStreet() + " , " + restroInfo.getLocation()+ " , " + restroInfo.getCity());
        capacityTextView.setText(restroInfo.getCapacity() + "");

    }

    private void findAllViewsId(){

        button = (Button) findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        numberOfSeats = (EditText) findViewById(R.id.numberOfSeats);
        contactNo = (EditText) findViewById(R.id.contactNo);
    }

    private Restaurant getRestaurant(String restroId) {
        return new Restaurant("id", "McDonalds", "Kharadi", "Kharadi", "Pune", 20, "12345");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBookingButtonClick(View v) {




        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("userName", name.getText().toString());
        jsonParams.put("userEmail", email.getText().toString());
        jsonParams.put("userContact", contactNo.getText().toString());
        jsonParams.put("noOfSeats", numberOfSeats.getText().toString());
       jsonParams.put("restoName", restroNameTextView.getText().toString());
       jsonParams.put("restoId", restroId);
       //jsonParams.put("bookingDate", new Date().toString());

        saveBookingInfo(jsonParams);

    }

    private void saveBookingInfo( Map<String, String> jsonParams) {
        String url = "http://10.0.2.2:8080/restrobooking/resources/booking";

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Formulate the request and handle the response.
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        Log.i("Map: ", new JSONObject(jsonParams).toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("", response.toString());
                Intent intent = new Intent(getApplicationContext(), BookingResultActivity.class);

                String bookingMessage = "Dear " + name.getText().toString() + ", your booking at " + restroNameTextView.getText().toString() + " restaurant for " + numberOfSeats.getText().toString() + " seats was successful!";
                intent.putExtra("bookingMessage", bookingMessage);

                //start the BookingResultActivity
                startActivity(intent);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("", error.toString());
            }
        });

        // Add the request to the RequestQueue.
        mRequestQueue.add(jsObjRequest);
    }
}
