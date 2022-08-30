package com.example.pizaaapp;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderApi {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private String myUrl;
    private OkHttpClient client;
    private final String API = "https://63045ba00de3cd918b45944a.mockapi.io/api/v1/";
    private String action;
    private Order order;
    private String jsonData;
    private Context currentContext;

    public OrderApi(Context currentContext,String action,Order order){
        this.currentContext = currentContext;
        this.action = action;
        this.order = order;

        client = new OkHttpClient();
        myUrl = API + "Order";

        if((action.equals("update") || action.equals("delete")) && order.getId() > 0){
            myUrl += "/"+order.getId();
        }

        jsonData = new Gson().toJson(order);

        setOrder();
    }


    public void setOrder(){
        RequestBody body = RequestBody.create(jsonData, JSON);
        Request request = null;

        if(action.equals("delete")){
            request = new Request.Builder()
                    .url(myUrl)
                    .delete()
                    .build();
        }else if(action.equals("insert")){
            request = new Request.Builder()
                    .url(myUrl)
                    .post(body)
                    .build();
        }else if(action.equals("update")){
            request = new Request.Builder()
                    .url(myUrl)
                    .put(body)
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                response.body().string();
                if(action.equals("insert") || action.equals("update")){
                    Intent intent = new Intent(currentContext,MainActivity.class);
                    currentContext.startActivity(intent);
                    ((AppCompatActivity)currentContext).finish();
                }

            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
        });
    }
}
