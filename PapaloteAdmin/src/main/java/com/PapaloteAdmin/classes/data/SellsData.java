package com.PapaloteAdmin.classes.data;

import com.PapaloteAdmin.classes.Sells;

public class SellsData extends Sells {
    private String waiter;
    private String date;

    public String getWaiter() {
        return waiter;
    }

    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
