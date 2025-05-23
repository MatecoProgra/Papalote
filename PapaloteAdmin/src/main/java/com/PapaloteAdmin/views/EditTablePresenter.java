package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.GeneralQueue;
import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.Tables;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Window;

public class EditTablePresenter {

    Tables table;

    @FXML
    private Spinner<Integer> capacityPicker;

    @FXML
    private View editTable;

    /**
     * @param event No utilizado
     * @@apiNote actualizamos la capacidad, lo editamos en la api y regresamos, eliminandolo de la cola
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        table.setCapacity(capacityPicker.getValue());
        Request.editTable(table);
        Views.switchView(Views.VIEW_CATS_VIEW);
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        GeneralQueue.tablesQueue.remove(table);
        Views.switchView(Views.VIEW_CATS_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos el campo y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        capacityPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1));

        editTable.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Editar mesa");

                table = GeneralQueue.tablesQueue.get(0);
                capacityPicker.getValueFactory().setValue(table.getCapacity());

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(editTable.getPrefHeight() + 100);
                window.setWidth(editTable.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }
}
