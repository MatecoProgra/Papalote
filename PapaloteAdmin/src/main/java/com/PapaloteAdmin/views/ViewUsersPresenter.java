package com.PapaloteAdmin.views;

import com.PapaloteAdmin.Main;
import com.PapaloteAdmin.classes.GeneralQueue;
import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.User;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.util.Optional;

public class ViewUsersPresenter {

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, Integer> idCol;

    @FXML
    private TableColumn<User, String> nameCol;

    @FXML
    private View viewUsers;

    @FXML
    void backAction(ActionEvent event) {
        Views.switchView(Views.MAIN_VIEW);
    }
    /**
     * @param event No utilizado
     * @@apiNote Validamos que exista un usuario seleccionado valido, confirmamos con el alert y eliminamos
     */
    @FXML
    void deleteSelectedUser(ActionEvent event) {
        User userTemp = usersTable.getSelectionModel().getSelectedItem();
        if(userTemp != null && userTemp!= Main.getUser() && userTemp.getId() != 1){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Deseas eliminar el usuario?");

            ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
            ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
            alert.getButtonTypes().setAll(confirm, cancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == confirm) {
                User user = usersTable.getSelectionModel().getSelectedItem();
                Request.deleteUser(user.getId());
                usersTable.getItems().remove(user);
            }
        }
    }
    /**
     * @param event No utilizado
     * @@apiNote Guardamos el usuario en la cola de usuarios y cambiamos la vista
     */
    @FXML
    void editSelectedUser(ActionEvent event) {
        GeneralQueue.usersQueue.add(usersTable.getSelectionModel().getSelectedItem());
        Views.switchView(Views.EDIT_USER_VIEW);
    }
    //Agregamos un nuevo usuario
    @FXML
    void newUser(ActionEvent event) {
        Views.switchView(Views.NEW_USER_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos las tablas y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        viewUsers.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppManager manager = AppManager.getInstance();
                AppBar appBar = manager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        manager.getDrawer().open()));
                appBar.setTitleText("Usuarios");

                usersTable.getItems().clear();
                usersTable.getItems().addAll(Request.getUsers());

                Window window = manager.getView().getScene().getWindow();
                window.setHeight(viewUsers.getPrefHeight() + 100);
                window.setWidth(viewUsers.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }
}
