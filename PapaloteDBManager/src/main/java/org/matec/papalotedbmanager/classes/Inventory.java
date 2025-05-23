package org.matec.papalotedbmanager.classes;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int id_product;

    @Column
    private int quantity;

    @Column
    private LocalDate last_update;

    @Column
    private int max_stock;

    @Column
    private int min_stock;

    public int getId() {
        return id;
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

    public void setLast_update(LocalDate lastUpdate) {
        this.last_update = lastUpdate;
    }

    public int getMax_stock() {
        return max_stock;
    }

    public void setMax_stock(int maxStock) {
        this.max_stock = maxStock;
    }

    public int getMin_stock() {
        return min_stock;
    }

    public void setMin_stock(int minStock) {
        this.min_stock = minStock;
    }
}
