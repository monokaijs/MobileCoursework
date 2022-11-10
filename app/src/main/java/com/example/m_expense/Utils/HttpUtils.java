package com.example.m_expense.Utils;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
  public static OkHttpClient client = new OkHttpClient();

  public static void post(String url, RequestBody body, Callback callback) throws IOException {
    Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
    client.newCall(request).enqueue(callback);
  }
}