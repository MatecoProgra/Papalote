package com.PapaloteAdmin.classes.data;

import com.PapaloteAdmin.classes.Inventory;

public class InventoryData extends Inventory {
    private String product_name;
    private String last_upd;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getLast_upd() {
        return last_upd;
    }

    public void setLast_upd(String last_upd) {
        this.last_upd = last_upd;
    }
}
