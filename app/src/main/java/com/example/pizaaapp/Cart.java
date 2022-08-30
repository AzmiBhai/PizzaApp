package com.example.pizaaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Cart extends AppCompatActivity {
    TextView subTotal,gst,netTotal;
    Button button;
    Order order;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    RecyclerView recyclerView;
    CartAdapter adapter;
    OkHttpClient client;
    private static final String API  = "https://63045ba00de3cd918b45944a.mockapi.io/api/v1/Cart";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        subTotal = findViewById(R.id.subTotal);
        gst = findViewById(R.id.gst);
        netTotal = findViewById(R.id.netTotal);
        button = findViewById(R.id.pay);

        client = new OkHttpClient();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cart.this,RecyclerView.VERTICAL,false));

        ArrayList<CartPizza> items = getAllCartItem();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order = new Order();

                List<Double> price =  items.stream().map(CartPizza::getPrice).collect(Collectors.toList());


                double t=0;
                for (int i = 0; i < price.size(); i++) {
                    t += price.get(i);
                }
                double nT = t + t*0.18;


                ArrayList<Integer> cartId = (ArrayList<Integer>) items.stream().map(CartPizza::getId).collect(Collectors.toList());

                Log.d("CartId", cartId.toString());


                order.setCartId(cartId);
                order.setAmount(nT);
                order.setUserId(101);

                new OrderApi(Cart.this,"insert",order);
            }
        });



    }


    public ArrayList<CartPizza> getAllCartItem(){

        ArrayList<CartPizza> list = new ArrayList<>();

        Request request = new Request.Builder()
                .url(API)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    String data = responseBody.string();

                    Cart.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            ArrayList<CartPizza> pizzaList = gson.fromJson(data,new TypeToken<ArrayList<CartPizza>>(){}.getType());
                            adapter = new CartAdapter(Cart.this,pizzaList);
                            recyclerView.setAdapter(adapter);

                            list.addAll(pizzaList);

                            List<Double> p =  pizzaList.stream().map(CartPizza::getPrice).collect(Collectors.toList());

                            double t1=0;
                            for (int i = 0; i < p.size(); i++) {
                                t1 += p.get(i);
                            }
                            double nT1 = t1 + t1*0.18;
                            subTotal.setText(String.valueOf(t1));
                            gst.setText("18 %");
                            netTotal.setText(String.valueOf(nT1));

                        }
                    });
                }
            }
        });

        return list;
    }

}