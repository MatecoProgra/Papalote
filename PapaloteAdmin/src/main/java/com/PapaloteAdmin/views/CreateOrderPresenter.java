package com.PapaloteAdmin.views;

import com.PapaloteAdmin.Main;
import com.PapaloteAdmin.classes.Order;
import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.Tables;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Window;

import java.time.LocalDateTime;

public class CreateOrderPresenter {

    @FXML
    private View createOrder;

    @FXML
    private ChoiceBox<Integer> tablePicker;


    /**
     * @param event No utilizao
     * @@apiNote Confirmamos que el valor de la choicebox no sea nulo y creamos la nueva orden
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        if (tablePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a table");
            alert.showAndWait();
            return;
        }
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(Order.ON_PROCESS);
        order.setTableId(tablePicker.getValue());
        order.setEmployeeId(Main.getUser().getId());
        Request.addOrder(order);
        Views.switchView(Views.NEW_ORDER_VIEW);
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Views.switchView(Views.NEW_ORDER_VIEW);
    }


    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos la tabla y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        createOrder.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Seleccionar mesa");

                for(Tables table : Request.getTables()) {
                    tablePicker.getItems().add(table.getId());
                }

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(createOrder.getPrefHeight() + 100);
                window.setWidth(createOrder.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }
}
