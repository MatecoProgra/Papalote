package com.PapaloteAdmin.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GeneralQueue {

    public static ObservableList<User> usersQueue = FXCollections.observableArrayList();
    public static ObservableList<Order> ordersQueue = FXCollections.observableArrayList();
    public static ObservableList<Product> productsQueue = FXCollections.observableArrayList();
    public static ObservableList<Tables> tablesQueue = FXCollections.observableArrayList();
    public static ObservableList<Inventory> inventoryQueue = FXCollections.observableArrayList();
    public static ObservableList<ProductCategory> productCategoryQueue = FXCollections.observableArrayList();
    public static ObservableList<UserCategory> userCategoryQueue = FXCollections.observableArrayList();
    public static ObservableList<OrderInfo> orderInfoQueue = FXCollections.observableArrayList();
    public static ObservableList<Product> selectedProductQueue = FXCollections.observableArrayList();
}
