package com.example.pizaaapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    ArrayList<CartPizza> pizzaList;

    public CartAdapter(Context context, ArrayList<CartPizza> pizzaList) {
        this.context = context;
        this.pizzaList = pizzaList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_cart,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CartPizza pizza = pizzaList.get(position);

        holder.cartPizzaName.setText(pizza.getName());
        holder.cartPizzaDescription.setText(pizza.getDescription());
        holder.cartPizzaPrice.setText(String.valueOf(pizza.getPrice()));

        holder.cartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CartApi(context,"delete",pizza);

                pizzaList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                Intent intent = new Intent(context,Cart.class);

                context.startActivity(intent);
                ((AppCompatActivity)context).finish();

            }

        });


    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cartPizzaImage,cartDelete;
        TextView cartPizzaName,cartPizzaDescription,cartPizzaPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartPizzaName = itemView.findViewById(R.id.cartPizzaName);
            cartPizzaDescription = itemView.findViewById(R.id.cartPizzaDescription);
            cartPizzaPrice = itemView.findViewById(R.id.cartPizzaPrice);
            cartPizzaImage = itemView.findViewById(R.id.cartPizzaImage);
            cartDelete = itemView.findViewById(R.id.cartDelete);
        }
    }
}
