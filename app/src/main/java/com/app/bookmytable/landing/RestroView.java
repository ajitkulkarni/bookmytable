package com.app.bookmytable.landing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.bookmytable.R;
import com.app.bookmytable.model.Restaurant;

public class RestroView extends RelativeLayout {
    private TextView restroNameTextView;
    private TextView restroAddressTextView;
    private TextView capacityTextView;

    public static RestroView inflate(ViewGroup parent) {
        RestroView restroView = (RestroView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restro_view, parent, false);
        return restroView;
    }

    public RestroView(Context c) {
        this(c, null);
    }

    public RestroView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RestroView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.restro_view_children, this, true);
        setupChildren();
    }

    private void setupChildren() {
        restroNameTextView = (TextView) findViewById(R.id.restro_name);
        restroAddressTextView = (TextView) findViewById(R.id.restro_address);
        capacityTextView = (TextView) findViewById(R.id.restro_capacity);
    }

    public void setRestro(Restaurant restaurant) {
        restroNameTextView.setText(restaurant.getName());
        restroAddressTextView.setText(restaurant.getStreet() + " , " + restaurant.getLocation()+ " , " + restaurant.getCity());
        capacityTextView.setText(restaurant.getCapacity() + "");
    }
}
