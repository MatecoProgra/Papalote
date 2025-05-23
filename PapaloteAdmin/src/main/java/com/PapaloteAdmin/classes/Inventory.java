package com.PapaloteAdmin.classes;

import java.time.LocalDate;

public class Inventory {
    private int id;
    private int id_product;
    private int quantity;
    private LocalDate last_update;
    private int max_stock;
    private int min_stock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getLast_update() {
        return last_update;
    }

    public void setLast_update(LocalDate last_update) {
        this.last_update = last_update;
    }

    public int getMax_stock() {
        return max_stock;
    }

    public void setMax_stock(int max_stock) {
        this.max_stock = max_stock;
    }

    public int getMin_stock() {
        return min_stock;
    }

    public void setMin_stock(int min_stock) {
        this.min_stock = min_stock;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id_product\":" + id_product +
                ", \"quantity\":" + quantity +
                ", \"last_update\":" + "\""+last_update + "\""+
                ", \"max_stock\":" + max_stock +
                ", \"min_stock\":" + min_stock +
                '}';
    }
}
