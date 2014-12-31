package com.app.bookmytable.landing;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.app.bookmytable.model.Restaurant;

import java.util.List;

/**
 * Created by akulkarni on 12/31/2014.
 */
public class RestroAdapter extends ArrayAdapter<Restaurant> {
    public RestroAdapter(Context c, List<Restaurant> restaurantList) {
        super(c, 0, restaurantList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RestroView restroView = (RestroView)convertView;
        if (null == restroView)
            restroView = restroView.inflate(parent);
        restroView.setRestro(getItem(position));
        return restroView;
    }
}
