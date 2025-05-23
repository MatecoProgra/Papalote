package com.PapaloteAdmin.classes;

public class SellDetail {
    private int id;
    private int id_sell;
    private int id_product;
    private int quantity;
    private double unit_price;
    private double subtotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_sell() {
        return id_sell;
    }

    public void setId_sell(int id_sell) {
        this.id_sell = id_sell;
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

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id_sell\":" + id_sell +
                ", \"id_product\":" + id_product +
                ", \"unit_price\":" + unit_price +
                ", \"quantity\":" + quantity +
                ", \"total_price\":" + subtotal +
                '}';
    }
}
