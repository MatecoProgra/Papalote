package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.User;
import com.PapaloteAdmin.classes.UserCategory;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Window;


public class NewUserPresenter {

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private ChoiceBox<String> categoryMenu;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField pass1Field;

    @FXML
    private TextField pass2Field;

    @FXML
    private View newUser;

    @FXML
    void cancelButtonPress(ActionEvent event) {
        Views.switchView(Views.VIEW_USERS_VIEW);
    }
    /**
     * @param event No utilizado
     * @@apiNote validamos los datos, lo enviamos a la api y regresamos
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        if(validate()) {

            User newUser = new User();
            newUser.setName(nameField.getText());
            newUser.setEmail(emailField.getText());

            newUser.setPassword(pass1Field.getText());
            newUser.setBirthday(birthdatePicker.getValue());
            newUser.setCategory(1);
            Request.addUser(newUser);

            Views.switchView(Views.VIEW_USERS_VIEW);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ingrese correctamente los datos del usuario.");
        }
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos los campos y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        newUser.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Nuevo usuario");

                birthdatePicker.setValue(null);
                nameField.setText("");
                emailField.setText("");
                pass1Field.setText("");
                pass2Field.setText("");
                categoryMenu.getItems().clear();
                ObservableList<String> cats = FXCollections.observableArrayList();
                for(UserCategory cat: Request.getUserCategories()){
                    cats.add(cat.getName());
                }
                categoryMenu.setItems(cats);

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(newUser.getPrefHeight() + 100);
                window.setWidth(newUser.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }

    /**
     * @return Devuelve la validacion de toda la informacion del usuario
     */
    private boolean validate(){
        boolean result = true;
        result = result && !emailField.getText().isBlank();
        result = result && !nameField.getText().isBlank();
        result = result && !pass1Field.getText().isBlank();
        result = result && !pass2Field.getText().isBlank();
        result = result && pass1Field.getText().length()>4 && pass2Field.getText().length()>4;
        result = result && pass1Field.getText().equals(pass2Field.getText());
        result = result && !categoryMenu.getValue().isBlank();
        result = result && birthdatePicker.getValue() != null;
        return result;
    }
}
