package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.GeneralQueue;
import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.User;
import com.PapaloteAdmin.classes.UserCategory;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Window;

public class EditUserPresenter {

    User user;

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private ChoiceBox<String> categoryMenu;

    @FXML
    private View editUser;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField pass1Field;

    @FXML
    private TextField pass2Field;

    /**
     * @param event No utilizado
     * @@apiNote validamos los datos, lo enviamos a la api y regresamos
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        if (validate()) {
            user.setName(nameField.getText());
            user.setEmail(emailField.getText());
            user.setPassword(pass1Field.getText());
            user.setPassword(pass2Field.getText());
            user.setBirthday(birthdatePicker.getValue());
            String catname = categoryMenu.getSelectionModel().getSelectedItem();
            String[] div = catname.split("_");
            user.setCategory(Integer.parseInt(div[1]));
            Request.editUser(user);
            GeneralQueue.usersQueue.remove(user);
            System.out.println("Usuario actualizado");
            Views.switchView(Views.VIEW_CATS_VIEW);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ingrese correctamente los datos del usuario.");
        }
    }

    @FXML
    void CancelButtonPress(ActionEvent event) {
        GeneralQueue.usersQueue.remove(user);
        Views.switchView(Views.VIEW_CATS_VIEW);
    }
    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos los campos y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        editUser.showingProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Editar usuario");

                user = GeneralQueue.usersQueue.get(0);
                birthdatePicker.setValue(user.getBirthday());
                emailField.setText(user.getEmail());
                nameField.setText(user.getName());
                pass1Field.setText(user.getPassword());
                pass2Field.setText(user.getPassword());

                categoryMenu.getItems().clear();
                categoryMenu.setDisable(false);
                ObservableList<UserCategory> cats = Request.getUserCategories();
                if(!cats.isEmpty()){
                    for (UserCategory cat : cats) {
                        categoryMenu.getItems().add(cat.getName() + "_" + cat.getId());
                        if(user.getCategory()==cat.getId())
                            categoryMenu.getSelectionModel().select(cat.getId());
                    }
                }
                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(editUser.getPrefHeight() + 100);
                window.setWidth(editUser.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }

    private boolean validate() {
        boolean result = true;
        result = !emailField.getText().isBlank();
        result = result && !nameField.getText().isBlank();
        result = result && !pass1Field.getText().isBlank();
        result = result && !pass2Field.getText().isBlank();
        result = result && pass1Field.getText().length() > 4 && pass2Field.getText().length() > 4;
        result = result && pass1Field.getText().equals(pass2Field.getText());
        result = result && !categoryMenu.getValue().isBlank();
        result = result && birthdatePicker.getValue() != null;
        return result;
    }
}