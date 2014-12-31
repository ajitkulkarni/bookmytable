package com.app.bookmytable.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private String id;

    private String name;

    private String location;

    private String street;

    private String city;

    private int capacity;

    private String contactNumber;

    public Restaurant(String id, String name, String location, String street, String city, int capacity, String contactNumber) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.street = street;
        this.city = city;
        this.capacity = capacity;
        this.contactNumber = contactNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public static List<Restaurant> fromJson(JSONArray array) {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        if (array != null && array.length() > 0) {
            for (int i=0; i < array.length(); i++) {
                JSONObject restaurantsJson = null;
                try {
                    restaurantsJson = array.getJSONObject(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                Restaurant restaurant = Restaurant.fromJson(restaurantsJson);
                if (restaurant != null) {
                    restaurants.add(restaurant);
                }
            }
        }
        return restaurants;
    }

    public static Restaurant fromJson(JSONObject object) {

        try {
            return new Restaurant(String.valueOf(object.get("_id")), String.valueOf(object.get("name")), String.valueOf(object.get("location")), String.valueOf(object.get("street")), String.valueOf(object.get("city")), Integer.valueOf(String.valueOf(object.get("capacity"))), String.valueOf(object.get("contactNumber")));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object

    }
}
