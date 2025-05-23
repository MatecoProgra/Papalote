package com.PapaloteAdmin.classes;

public class Product {

    private int id;
    private String name;
    private double price;
    private String description;
    private int category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "{" +
                " \"name\":\"" + name + '\"' +
                ", \"price\":" + price +
                ", \"description\":\"" + description + '\"' +
                ", \"category\":" + category +
                '}';
    }
}
