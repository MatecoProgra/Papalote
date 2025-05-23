package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.ProductCategory;
import com.PapaloteAdmin.classes.Request;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Window;

public class NewProductCategoryPresenter {

    @FXML
    private TextArea descArea;

    @FXML
    private TextField nameField;

    @FXML
    private View newProdCat;

    /**
     * @param event No utilizado
     * @@apiNote Validamos los datos, enviamos al api y regresamos
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        if(!nameField.getText().isEmpty() && !descArea.getText().isEmpty()) {
            ProductCategory newProdCat = new ProductCategory();
            newProdCat.setName(nameField.getText());
            newProdCat.setDescription(descArea.getText());
            Request.addProductCategory(newProdCat);
            Views.switchView(Views.VIEW_CATS_VIEW);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ingrese correctamente los datos.");
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Views.switchView(Views.VIEW_CATS_VIEW);
    }
    /**
     * @@apiNote Cambiamos el titulo en la appbar, asignamos los campos y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        newProdCat.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Nueva categoria");

                nameField.setText("");
                descArea.setText("");

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(newProdCat.getPrefHeight() + 100);
                window.setWidth(newProdCat.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }
}
