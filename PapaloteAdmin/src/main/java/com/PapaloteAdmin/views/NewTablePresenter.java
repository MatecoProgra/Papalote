package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.Tables;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Window;

public class NewTablePresenter {

    @FXML
    private Spinner<Integer> capacityPicker;

    @FXML
    private View newTable;

    /**
     * @param event No utilizado
     * @@apiNote actualizamos los datos en la api y regresamos
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        Tables newTable = new Tables();
        newTable.setCapacity(capacityPicker.getValue());
        newTable.setState(Tables.FREE);
        Request.addTable(newTable);
        Views.switchView(Views.VIEW_CATS_VIEW);
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Views.switchView(Views.VIEW_CATS_VIEW);
    }
    /**
     * @@apiNote Cambiamos el titulo en la appbar, asignamos los campos y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        capacityPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1));

        newTable.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Nueva mesa");

                capacityPicker.getValueFactory().setValue(1);

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(newTable.getPrefHeight() + 100);
                window.setWidth(newTable.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }

}
