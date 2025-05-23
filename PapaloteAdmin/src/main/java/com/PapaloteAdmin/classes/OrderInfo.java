package com.PapaloteAdmin.classes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderInfo {

    public final static String SENDED = "Enviado";
    public final static String PENDIENT = "Sin enviar";

    private int id;

    private int productId;

    private int orderId;

    private int quantity;

    private String comments;

    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "{" +
                "\"productId\":" + productId +
                ", \"orderId\":" + orderId +
                ", \"quantity\":" + quantity +
                ", \"comments\":" + "\""+ comments + "\"" +
                ", \"state\":" + "\""+ state + "\"" +
                '}';
    }
}
