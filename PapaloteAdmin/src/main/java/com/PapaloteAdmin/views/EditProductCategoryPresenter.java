package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.GeneralQueue;
import com.PapaloteAdmin.classes.OrderInfo;
import com.PapaloteAdmin.classes.ProductCategory;
import com.PapaloteAdmin.classes.Request;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Window;

public class EditProductCategoryPresenter {
    ProductCategory productCategory;
    @FXML
    private TextArea descArea;

    @FXML
    private TextField nameField;

    @FXML
    private View newProdCat;

    /**
     * @param event No utilizado
     * @@apiNote Si la informacion es valida, lo editamos en la api y regresamos, eliminandolo de la cola
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        if(!descArea.getText().isEmpty() && !nameField.getText().isEmpty()) {
            productCategory.setDescription(descArea.getText());
            productCategory.setName(nameField.getText());
            Request.editProductCategory(productCategory);
            GeneralQueue.productCategoryQueue.remove(0);
            Views.switchView(Views.VIEW_CATS_VIEW);
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        GeneralQueue.productCategoryQueue.remove(0);
        Views.switchView(Views.VIEW_CATS_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos los campos y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        newProdCat.showingProperty().addListener((observableValue, old, newV) ->{
            if (newV){
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Editar producto");

                productCategory = GeneralQueue.productCategoryQueue.get(0);
                descArea.setText(productCategory.getDescription());
                nameField.setText(productCategory.getName());

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(newProdCat.getPrefHeight() + 100);
                window.setWidth(newProdCat.getPrefWidth());
                window.centerOnScreen();
            }
        } );
    }

}
