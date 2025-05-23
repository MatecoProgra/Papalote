package org.matec.papalotedbmanager.classes;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private int id_employee;

    @Column
    private LocalDateTime date_order;

    @Column
    private double subtotal;

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

    public int getId() {
        return id;
    }
}
