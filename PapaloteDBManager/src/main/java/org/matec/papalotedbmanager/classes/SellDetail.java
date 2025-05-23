package org.matec.papalotedbmanager.classes;

import jakarta.persistence.*;

@Entity
public class SellDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int id_sell;

    @Column(nullable = false)
    private int id_product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unit_price;

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

}
