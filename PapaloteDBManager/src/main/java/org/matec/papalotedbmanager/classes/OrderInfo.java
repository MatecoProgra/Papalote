package org.matec.papalotedbmanager.classes;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OrderInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private int orderId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String comments;

    @Column(nullable = false)
    private String state;

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
