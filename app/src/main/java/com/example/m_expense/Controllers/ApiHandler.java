package com.example.m_expense.Controllers;

import android.util.Log;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.Elements.PostData;
import com.example.m_expense.Elements.Trip;
import com.example.m_expense.Utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ApiHandler {
  public static String BASE_URL = "http://cwservice1786.herokuapp.com/sendPayLoad";

  public static void postAllTrips(Callback callback) throws IOException {
    TripDatabaseHandler tripDb = new TripDatabaseHandler(MainActivity.getInstance());
    List<Trip> trips = tripDb.getAllTrips();
    String json = new Gson().toJson(new PostData(trips));
    Log.i("JSON", json);
    RequestBody formBody = RequestBody.Companion.create("jsonpayload=" + json, MediaType.get("application/x-www-form-urlencoded"));
    HttpUtils.post(BASE_URL, formBody, callback);
  }
}
