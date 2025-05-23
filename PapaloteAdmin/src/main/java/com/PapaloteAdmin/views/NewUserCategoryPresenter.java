package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.UserCategory;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Window;

public class NewUserCategoryPresenter {

    @FXML
    private CheckBox adminCheck;

    @FXML
    private TextField nameField;

    @FXML
    private View newUserCat;

    /**
     * @param event No utilizado
     * @@apiNote Actualizamos los datos en la api y regresamos
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        UserCategory category = new UserCategory();
        category.setName(nameField.getText());
        category.setAdmin(adminCheck.isSelected());
        Request.addUserCategory(category);
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
        newUserCat.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Nueva categoria");

                nameField.setText("");

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(newUserCat.getPrefHeight() + 100);
                window.setWidth(newUserCat.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }
}
