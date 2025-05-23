package com.PapaloteAdmin.classes;

import java.time.LocalDateTime;

public class Order {

    public static String ON_PROCESS = "En proceso";
    public static String FINISHED = "Finalizada";
    public static String CANCELLED = "Cancelada";

    private int id;
    private int tableId;
    private int userId;
    private LocalDateTime orderDate;
    private String orderStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getEmployeeId() {
        return userId;
    }

    public void setEmployeeId(int userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "{" +
                " \"tableId\":" + tableId +
                ", \"employeeId\":" + userId +
                ", \"orderDate\":\"" + orderDate + '\"' +
                ", \"orderStatus\":\"" + orderStatus + '\"' +
                '}';
    }
}
