package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.Request;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Window;

public class MainPresenter {

    @FXML
    private Button invButton;

    @FXML
    private Button prodButton;

    @FXML
    private Button takeButton;

    @FXML
    private View main;

    //Redirigimos a todas las secciones

    @FXML
    void seeCats(ActionEvent event) {
        Views.switchView(Views.VIEW_CATS_VIEW);
    }

    @FXML
    void seeInventory(ActionEvent event) {
        Views.switchView(Views.VIEW_INVENTORY_VIEW);
    }

    @FXML
    void seeProds(ActionEvent event) {
        Views.switchView(Views.VIEW_PRODS_VIEW);
    }

    @FXML
    void seeSellDetail(ActionEvent event) {
        Views.switchView(Views.VIEW_SELL_DETAILS_VIEW);
    }

    @FXML
    void seeSells(ActionEvent event) {
        Views.switchView(Views.VIEW_SELLS_VIEW);
    }

    @FXML
    void takeOrder(ActionEvent event) {
        Views.switchView(Views.NEW_ORDER_VIEW);
    }

    @FXML
    void showOrders(ActionEvent event) {
        Views.switchView(Views.VIEW_ORDER);
    }

    @FXML
    void seeUsers(ActionEvent event) {
        Views.switchView(Views.VIEW_USERS_VIEW);
    }


    /**
     * @@apiNote Validamos que existan productos, categorias de productos y mesas y luego cambiamos el tamaño de la pantalla
     */
    public void initialize() {
        main.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Inicio");

                takeButton.setDisable(Request.getTables().isEmpty() || Request.getProducts().isEmpty());
                prodButton.setDisable(Request.getProductCategories().isEmpty());
                invButton.setDisable(Request.getProducts().isEmpty());

                AppManager manager = AppManager.getInstance();
                Window window = manager.getView().getScene().getWindow();
                window.setHeight(main.getPrefHeight() + 100);
                window.setWidth(main.getPrefWidth());
                window.centerOnScreen();
                appBar.setStyle("-fx-background-color: #d52513");
                manager.getDrawer().setStyle("-fx-background-color: #d52513");
            }
        });
    }
}
