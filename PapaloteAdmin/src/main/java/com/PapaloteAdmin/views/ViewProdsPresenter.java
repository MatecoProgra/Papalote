package com.PapaloteAdmin.views;


import com.PapaloteAdmin.classes.GeneralQueue;
import com.PapaloteAdmin.classes.Product;
import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.data.ProductData;
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

import java.util.Objects;
import java.util.Optional;

public class ViewProdsPresenter {

    @FXML
    private TableColumn<ProductData, String> catCol;

    @FXML
    private TableColumn<ProductData, String> descCol;

    @FXML
    private TableColumn<ProductData, Integer> idCol;

    @FXML
    private TableColumn<ProductData, String> nameCol;

    @FXML
    private TableColumn<ProductData, Double> priceCol;

    @FXML
    private TableView<ProductData> productTable;

    @FXML
    private View viewProducts;

    @FXML
    void backAction(ActionEvent event) {
        Views.switchView(Views.MAIN_VIEW);
    }

    @FXML
    void deleteSelectedProd(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("Deseas eliminar el producto?");

        ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
        ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
        alert.getButtonTypes().setAll(confirm, cancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == confirm) {
            Product prod = productTable.getSelectionModel().getSelectedItem();
            Request.deleteProduct(prod.getId());
            productTable.getItems().remove(prod);
        }
    }

    @FXML
    void editSelectedProduct(ActionEvent event) {
        GeneralQueue.productsQueue.add(productTable.getSelectionModel().getSelectedItem());
        Views.switchView(Views.EDIT_PRODUCT_VIEW);
    }

    @FXML
    void newProduct(ActionEvent event) {
        Views.switchView(Views.NEW_PRODUCT_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos las tablas y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        catCol.setCellValueFactory(new PropertyValueFactory<>("catName"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        viewProducts.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppManager manager = AppManager.getInstance();
                AppBar appBar = manager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        manager.getDrawer().open()));
                appBar.setTitleText("Productos");

                Window window = manager.getView().getScene().getWindow();
                window.setHeight(viewProducts.getPrefHeight() + 100);
                window.setWidth(viewProducts.getPrefWidth());
                window.centerOnScreen();

                productTable.getItems().clear();
                for(Product p: Request.getProducts()){
                    ProductData pd = new ProductData();
                    pd.setId(p.getId());
                    pd.setName(p.getName());
                    pd.setCategory(p.getCategory());
                    pd.setDescription(p.getDescription());
                    pd.setPrice(p.getPrice());
                    pd.setCatName(Objects.requireNonNull(Request.getProductCategory(pd.getCategory())).getName());
                    productTable.getItems().add(pd);
                }
            }
        });
    }

}
