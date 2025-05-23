package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.GeneralQueue;
import com.PapaloteAdmin.classes.Order;
import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.data.OrderData;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class ViewOrderPresenter {

    @FXML
    private TableColumn<OrderData, String> dateCol;

    @FXML
    private TableColumn<OrderData, Integer> idCol;

    @FXML
    private TableColumn<OrderData, String> stateCol;

    @FXML
    private TableColumn<OrderData, Integer> tableCol;

    @FXML
    private TableView<OrderData> orderTable;

    @FXML
    private View viewOrder;

    @FXML
    private TableColumn<OrderData, String> waiterCol;

    @FXML
    void backAction(ActionEvent event) {
        Views.switchView(Views.MAIN_VIEW);
    }

    /**
     * @param event No utilizado
     * @@apiNote Validamos que exista una orden seleccionada, confirmamos con el alert y eliminamos
     */
    @FXML
    void deleteSelectedOrder(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("Deseas eliminar el producto?");

        ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
        ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
        alert.getButtonTypes().setAll(confirm, cancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == confirm) {
            Order order = orderTable.getSelectionModel().getSelectedItem();
            Request.deleteOrder(order.getId());
            orderTable.getItems().remove(order);
        }
    }
    /**
     * @param event No utilizado
     * @@apiNote Guardamos la orden en la cola y redirigimos
     */
    @FXML
    void editSelectedOrder(ActionEvent event) {
        if(orderTable.getSelectionModel().getSelectedItem() != null) {
            GeneralQueue.ordersQueue.add(orderTable.getSelectionModel().getSelectedItem());
            Views.switchView(Views.EDIT_ORDER_VIEW);
        }
    }
    //Redirigimos
    @FXML
    void newOrder(ActionEvent event) {
        Views.switchView(Views.NEW_ORDER_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos las tablas y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date_str"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        tableCol.setCellValueFactory(new PropertyValueFactory<>("id_table"));
        waiterCol.setCellValueFactory(new PropertyValueFactory<>("waiter_name"));
        viewOrder.showingProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                AppManager manager = AppManager.getInstance();
                AppBar appBar = manager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        manager.getDrawer().open()));
                appBar.setTitleText("Ordenes");

                orderTable.getItems().clear();
                ObservableList<OrderData> temp  = FXCollections.observableArrayList();
                for(Order order: Request.getOrders()){
                    OrderData data = new OrderData();
                    data.setOrderDate(order.getOrderDate());
                    data.setId(order.getId());
                    data.setOrderStatus(order.getOrderStatus());
                    data.setEmployeeId(order.getEmployeeId());
                    data.setTableId(order.getTableId());
                    data.setDate_str(data.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    data.setWaiter_name(Objects.requireNonNull(Request.getUser(data.getId())).getName());
                    temp.add(data);
                }
                orderTable.setItems(temp);

                Window window = manager.getView().getScene().getWindow();
                window.setHeight(viewOrder.getPrefHeight() + 100);
                window.setWidth(viewOrder.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }

}
