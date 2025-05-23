package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.GeneralQueue;
import com.PapaloteAdmin.classes.Product;
import com.PapaloteAdmin.classes.ProductCategory;
import com.PapaloteAdmin.classes.Request;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.stage.Window;

public class EditProdPresenter {

    Product product;

    @FXML
    private ChoiceBox<String> categoryMenu;

    @FXML
    private TextArea descript;

    @FXML
    private View editProduct;

    @FXML
    private TextField nameField;

    @FXML
    private Spinner<Double> price;

    /**
     * @param event No utilizado
     * @@apiNote Validamos los datos y mandamos editar el producto a spring
     */
    @FXML
    void OkButtonPress(ActionEvent event) {
        if(validate()){
            product.setName(nameField.getText());
            product.setDescription(descript.getText());
            String catname = categoryMenu.getSelectionModel().getSelectedItem();
            String[] div = catname.split("_");
            product.setCategory(Integer.parseInt(div[1]));
            product.setPrice(price.getValue());

            Request.editProduct(product);
            GeneralQueue.productsQueue.remove(product);
            Views.switchView(Views.VIEW_PRODS_VIEW);
        }
    }

    @FXML
    void cancelButtonPress(ActionEvent event) {
        GeneralQueue.productsQueue.remove(product);
        Views.switchView(Views.VIEW_PRODS_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos la tabla y cambiamos las dimensiones de la pantalla
     */
    public void initialize(){
        price.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(5, Double.MAX_VALUE, 5));
        price.getValueFactory().setValue(5.0);
        editProduct.showingProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Editar producto");

                product = GeneralQueue.productsQueue.get(0);
                nameField.setText(product.getName());
                descript.setText(product.getDescription());
                price.getValueFactory().setValue(product.getPrice());
                categoryMenu.getItems().clear();

                ObservableList<String> cats = FXCollections.observableArrayList();
                for(ProductCategory cat: Request.getProductCategories())
                    cats.add(cat.getName());

                categoryMenu.setItems(cats);
                categoryMenu.getSelectionModel().select(product.getCategory());

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(editProduct.getPrefHeight() + 100);
                window.setWidth(editProduct.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }

    /**
     * @return Devuelve la validacion de los campos de nombre, descripcion, precio y categoria
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
