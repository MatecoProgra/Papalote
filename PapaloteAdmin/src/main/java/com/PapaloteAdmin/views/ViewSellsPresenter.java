package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.Sells;
import com.PapaloteAdmin.classes.data.SellsData;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ViewSellsPresenter {

    @FXML
    private TableColumn<SellsData, String> dateCol;

    @FXML
    private TableColumn<SellsData, Integer> idCol;

    @FXML
    private TableColumn<SellsData, Double> subtotalCol;

    @FXML
    private TableView<SellsData> sellsTable;

    @FXML
    private View viewSells;

    @FXML
    private TableColumn<SellsData, String> waiterCol;

    @FXML
    void backAction(ActionEvent event) {
        Views.switchView(Views.MAIN_VIEW);
    }
    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos las tablas y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        dateCol.setCellValueFactory( new PropertyValueFactory<>("date"));
        idCol.setCellValueFactory( new PropertyValueFactory<>("id"));
        subtotalCol.setCellValueFactory( new PropertyValueFactory<>("subtotal"));
        waiterCol.setCellValueFactory( new PropertyValueFactory<>("waiter"));

        viewSells.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppManager manager = AppManager.getInstance();
                AppBar appBar = manager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        manager.getDrawer().open()));
                appBar.setTitleText("Ventas");

                sellsTable.getItems().clear();
                ObservableList<SellsData> sellsData = FXCollections.observableArrayList();
                for(Sells sell: Request.getSells()){
                    SellsData data = new SellsData();
                    data.setId(sell.getId());
                    data.setDate_order(sell.getDate_order());
                    data.setSubtotal(sell.getSubtotal());
                    data.setId_employee(sell.getId_employee());
                    data.setWaiter(Request.getUser(sell.getId_employee()).getName());
                    data.setDate(data.getDate_order().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    sellsData.add(data);
                }
                sellsTable.getItems().addAll(sellsData);

                Window window = manager.getView().getScene().getWindow();
                window.setHeight(viewSells.getPrefHeight() + 100);
                window.setWidth(viewSells.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }

}
