package com.PapaloteAdmin.views;

import com.PapaloteAdmin.Main;
import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.User;
import com.PapaloteAdmin.classes.UserCategory;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;


public class LoginPresenter {
    ObservableList<User> users;
    @FXML
    private View login;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField user;


    /**
     * @@apiNote Eliminamos el appbar, actualizamos los usuarios y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        login.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setVisible(false);
                AppManager.getInstance().getView().getScene().getWindow().setHeight(login.getPrefHeight()+50);
                AppManager.getInstance().getView().getScene().getWindow().setWidth(login.getPrefWidth()+20);
                users =Request.getUsers();
            }
        });
    }

    /**
     * @param event No utilizado
     * @@apiNote validamos los datos, verificamos con la api e ingresamos
     */
    @FXML
    void goMenu(ActionEvent event) {
        for(User u: users){
            if(u.getCategory()==1){
                if (u.getName().equals(user.getText()) && u.getPassword().equals(pass.getText())) {
                    Main.setUser(u);
                    AppManager.getInstance().switchView(Views.MAIN_VIEW);
                    return;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Nombre de usuario o contraseña incorrecto");
        alert.showAndWait();

    }
}
