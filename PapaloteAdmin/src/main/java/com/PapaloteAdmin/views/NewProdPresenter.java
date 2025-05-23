package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.Product;
import com.PapaloteAdmin.classes.ProductCategory;
import com.PapaloteAdmin.classes.Request;
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
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.util.StringConverter;

public class NewProdPresenter {

    @FXML
    private ChoiceBox<String> categoryMenu;

    @FXML
    private TextArea descript;

    @FXML
    private TextField nameField;

    @FXML
    private View newProduct;

    @FXML
    private Spinner<Double> price;

    /**
     * @param event No utilizado
     *
     * @@apiNote Validamos los datos, enviamos al api y regresamos
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        if(validate()) {
            Product p = new Product();
            p.setName(nameField.getText());
            p.setDescription(descript.getText());
            p.setPrice(price.getValue());
            String catname = categoryMenu.getSelectionModel().getSelectedItem();
            String[] div = catname.split("_");
            p.setCategory(Integer.parseInt(div[1]));
            Request.addProduct(p);
            Views.switchView(Views.VIEW_PRODS_VIEW);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ingrese correctamente los datos.");
        }
    }

    @FXML
    void cancelButtonPress(ActionEvent event) {
        Views.switchView(Views.VIEW_PRODS_VIEW);
    }
    /**
     * @@apiNote Cambiamos el titulo en la appbar, asignamos los campos y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        price.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(5, Double.MAX_VALUE, 5));
        price.getValueFactory().setValue(5.0);
        categoryMenu.setStyle("-fx-text-fill: white;");

        newProduct.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Nuevo producto");
                nameField.setText("");
                descript.setText("");

                categoryMenu.getItems().clear();
                ObservableList<ProductCategory> cats = Request.getProductCategories();
                for(ProductCategory cat : cats) {
                    categoryMenu.getItems().add(cat.getName() + "_" + cat.getId());
                }

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(newProduct.getPrefHeight() + 100);
                window.setWidth(newProduct.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }

    /**
     * @return Devuelve la validacion de el nombre, descipcion, precio y categoria
     */
    private boolean validate(){
        boolean valid = true;
        valid = valid && !nameField.getText().isEmpty();
        valid = valid && !descript.getText().isEmpty();
        valid = valid && price.getValue() > 0;
        valid = valid && categoryMenu.getValue() != null;
        return valid;
    }
}
