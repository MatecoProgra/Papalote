package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.GeneralQueue;
import com.PapaloteAdmin.classes.Inventory;
import com.PapaloteAdmin.classes.Request;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Window;

import java.time.LocalDate;

public class EditStockPresenter {

    Inventory inv;

    @FXML
    private Spinner<Integer> maxPicker;

    @FXML
    private Spinner<Integer> minPicker;

    @FXML
    private View newTable;

    @FXML
    private ChoiceBox<Integer> prodBox;

    @FXML
    private Spinner<Integer> stockPicker;
    /**
     * @param event No utilizado
     * @@apiNote  Lo editamos en la api y regresamos, eliminándolo de la cola
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        inv.setMax_stock(maxPicker.getValue());
        inv.setMin_stock(minPicker.getValue());
        inv.setQuantity(stockPicker.getValue());
        inv.setLast_update(LocalDate.now());
        System.out.println(inv.toString());
        Request.editInventory(inv);
        GeneralQueue.inventoryQueue.remove(inv);
        Views.switchView(Views.VIEW_INVENTORY_VIEW);
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        GeneralQueue.inventoryQueue.remove(inv);
        Views.switchView(Views.VIEW_INVENTORY_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos los campos y cambiamos las dimensiones de la pantalla
     * Establecemos los limites de los spinners y el aumento y disminucion automatico
     */
    public void initialize() {
        prodBox.setVisible(false);
        stockPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 1));
        maxPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 1));
        minPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 1));

        stockPicker.valueProperty().addListener((obs, oldValue, newValue) -> {
            //Si el stock es menor, bajamos el limite inferior
            if(stockPicker.getValue() <minPicker.getValue()){
                minPicker.getValueFactory().setValue(stockPicker.getValue());
            }
            //Si el stock es mayor, subimos el limite superior
            if(stockPicker.getValue() >maxPicker.getValue()){
                maxPicker.getValueFactory().setValue(stockPicker.getValue());
            }
        });

        minPicker.valueProperty().addListener((obs, oldValue, newValue) -> {
            //El minimo no puede ser mayor que el maximo, pero si iguales
            if(minPicker.getValue() > maxPicker.getValue())
                maxPicker.getValueFactory().setValue(minPicker.getValue());
            //Si el stock es menor, bajamos el limite inferior
            if(minPicker.getValue() > stockPicker.getValue())
                stockPicker.getValueFactory().setValue(minPicker.getValue());
        });

        maxPicker.valueProperty().addListener((obs, oldValue, newValue) -> {
            //El maximo no puede ser menor que el minimo, pero si iguales
            if(maxPicker.getValue() < minPicker.getValue())
                minPicker.getValueFactory().setValue(maxPicker.getValue());
            //Si el stock es menor, bajamos el limite inferior
            if (maxPicker.getValue() < stockPicker.getValue())
                stockPicker.getValueFactory().setValue(maxPicker.getValue());
        });

        newTable.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Editar inventario");

                inv = GeneralQueue.inventoryQueue.get(0);

                stockPicker.getValueFactory().setValue(inv.getQuantity());
                maxPicker.getValueFactory().setValue(inv.getMax_stock());
                minPicker.getValueFactory().setValue(inv.getMin_stock());


                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(newTable.getPrefHeight() + 100);
                window.setWidth(newTable.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }
}
