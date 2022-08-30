package com.example.pizaaapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int id,userId;
    private double amount;
    private ArrayList<Integer> cartId;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ArrayList<Integer> getCartId() {
        return cartId;
    }

    public void setCartId(ArrayList<Integer> cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                ", cartId=" + cartId +
                '}';
    }
}
