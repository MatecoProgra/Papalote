package com.PapaloteAdmin.classes.data;

import com.PapaloteAdmin.classes.Order;

public class OrderData extends Order {
    private String date_str;
    private String waiter_name;

    public String getDate_str() {
        return date_str;
    }

    public void setDate_str(String date_str) {
        this.date_str = date_str;
    }

    public String getWaiter_name() {
        return waiter_name;
    }

    public void setWaiter_name(String waiter_name) {
        this.waiter_name = waiter_name;
    }
}
