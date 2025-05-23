package com.PapaloteAdmin.classes;

import java.time.LocalDateTime;

public class Sells {
    private int id;

    private int id_employee;

    private LocalDateTime date_order;

    private double subtotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public LocalDateTime getDate_order() {
        return date_order;
    }

    public void setDate_order(LocalDateTime date_order) {
        this.date_order = date_order;
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
                " \"id_employee\":" + id_employee +
                ", \"date_order\":" + date_order +
                ", \"subtotal\":" + subtotal +
                '}';
    }
}
