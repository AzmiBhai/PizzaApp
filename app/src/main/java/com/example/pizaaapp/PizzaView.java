package com.example.pizaaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PizzaView extends AppCompatActivity {

    TextView pizzaName,pizzaDescription,pizzaPrice,pizzaQty;
    ImageView pizzaImage,minus,plus;
    Button addToCart;
    CartPizza cartPizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_view);

        pizzaName = findViewById(R.id.pizzaName);
        pizzaDescription = findViewById(R.id.pizzaDescription);
        pizzaPrice = findViewById(R.id.pizzaPrice);
        pizzaQty = findViewById(R.id.pizzaQty);
        pizzaImage = findViewById(R.id.pizzaImage);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        addToCart = findViewById(R.id.addToCart);

        Intent intent  = getIntent();

        Pizza pizza = (Pizza) intent.getSerializableExtra("pizza");

        pizzaName.setText(pizza.getName());
        pizzaDescription.setText(pizza.getDescription());
        pizzaPrice.setText("₹ "+String.valueOf(pizza.getPrice()));

        cartPizza = new CartPizza();


        Glide.with(PizzaView.this)
                .load(pizza.getImageUrl())
                .centerCrop()
                .into(pizzaImage);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = cartPizza.getQty();
                qty +=1;

                cartPizza.setQty(qty);
                pizzaQty.setText(String.valueOf(cartPizza.getQty()));
            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = cartPizza.getQty();

                if (qty <2){
                    qty = 1;
                }
                else {
                    qty -=1;
                }

                cartPizza.setQty(qty);
                pizzaQty.setText(String.valueOf(cartPizza.getQty()));
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CartPizza cartPizza1 = new CartPizza();
                cartPizza1.setUserId(pizza.getId());
                cartPizza1.setName(pizza.getName());
                cartPizza1.setPrice(pizza.getPrice());
                cartPizza1.setDescription(pizza.getDescription());
                cartPizza1.setImageUrl(pizza.getImageUrl());
                cartPizza1.setQty(cartPizza.getQty());

                new CartApi(PizzaView.this,"insert",cartPizza1);

                int currentStock = pizza.getStock() - cartPizza1.getQty();

                pizza.setStock(currentStock);

                new PizzaApi(PizzaView.this,"update",pizza);

            }
        });

    }
}