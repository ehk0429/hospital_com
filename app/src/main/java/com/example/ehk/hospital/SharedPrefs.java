package com.example.ehk.hospital;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefs {

    private static SharedPrefs instance;
    private SharedPreferenceUtil sharedPreferenceUtil;

    private SharedPrefs(Context context) {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
    }

    public synchronized static SharedPrefs getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefs(context);
        }
        return instance;
    }


    public void setReservations(List<Reservation> reservations) {
        sharedPreferenceUtil.put("reservations", new Gson().toJson(reservations));
    }

    public List<Reservation> getReservations() {
        String reservations = sharedPreferenceUtil.getString("reservations");
        List<Reservation> list = new Gson().fromJson(reservations, new TypeToken<List<Reservation>>() {
        }.getType());

        return list != null ? list : new ArrayList<Reservation>();
    }

}