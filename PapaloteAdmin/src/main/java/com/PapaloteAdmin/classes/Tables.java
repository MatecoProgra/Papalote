package com.PapaloteAdmin.classes;

public class Tables {

    public static String FREE = "Libre";
    public static String USED = "Ocupada";
    public static String NO_DISPONIBLE = "NO_DISPONIBLE";

    private int id;
    private int capacity;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "{" +
                " \"capacity\":" + capacity +
                ", \"state\":\"" + state + '\"' +
                '}';
    }
}
