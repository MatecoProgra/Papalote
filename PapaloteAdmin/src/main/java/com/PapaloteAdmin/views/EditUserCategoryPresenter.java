package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.GeneralQueue;
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

public class EditUserCategoryPresenter {

    UserCategory category;

    @FXML
    private CheckBox adminCheck;

    @FXML
    private TextField nameField;

    @FXML
    private View newUserCat;

    /**
     * @param event No utilizado
     * @@apiNote validamos el nombre, la editamos en la api y regresamos, eliminandolo de la cola
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        if(!nameField.getText().isEmpty()){
            category.setName(nameField.getText());
            category.setAdmin(adminCheck.isSelected());
            Request.editUserCategory(category);
            GeneralQueue.userCategoryQueue.remove(category);
            Views.switchView(Views.VIEW_CATS_VIEW);
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        GeneralQueue.userCategoryQueue.remove(category);
        Views.switchView(Views.VIEW_CATS_VIEW);
    }
    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos los campos y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        newUserCat.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Editar categoria");

                category = GeneralQueue.userCategoryQueue.get(0);
                nameField.setText(category.getName());
                adminCheck.setSelected(category.isAdmin());

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(newUserCat.getPrefHeight() + 100);
                window.setWidth(newUserCat.getPrefWidth()+ 20);
                window.centerOnScreen();
            }
        });
    }

}
