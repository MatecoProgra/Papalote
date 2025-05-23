package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.*;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.util.Optional;

public class ViewCatssPresenter {

    @FXML
    private TableColumn<Tables, Integer> capCol;

    @FXML
    private View catsView;

    @FXML
    private TableColumn<ProductCategory, Integer> idProdCat;

    @FXML
    private TableColumn<Tables, Integer> idTab;

    @FXML
    private TableColumn<UserCategory, Integer> idUserCat;

    @FXML
    private TableColumn<ProductCategory, String> prodCatName;

    @FXML
    private TableView<UserCategory> userCatTable;

    @FXML
    private TableView<ProductCategory> prodCatTable;

    @FXML
    private TableView<Tables> tableTable;

    @FXML
    private TableColumn<UserCategory, String> userCatName;

    @FXML
    void backAction(ActionEvent event) {
        Views.switchView(Views.MAIN_VIEW);
    }

    /**
     * @param event No utilizado
     * @@apiNote Validamos que exista una categoria o tabla seleccionada, confirmamos con el alert y eliminamos
     */
    @FXML
    void deleteObject(ActionEvent event) {
        Tables selectedTable = tableTable.getSelectionModel().getSelectedItem();
        UserCategory userCategory = userCatTable.getSelectionModel().getSelectedItem();
        ProductCategory productCategory = prodCatTable.getSelectionModel().getSelectedItem();
        if(selectedTable != null){
            for(Order order : Request.getOrders()){
                if(order.getTableId() == tableTable.getSelectionModel().getSelectedItem().getId()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("No se puede eliminar la mesa");
                    alert.setContentText("La mesa se encuentra en uso");
                    alert.showAndWait();
                    return;
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Deseas eliminar?");

            ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
            ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
            alert.getButtonTypes().setAll(confirm, cancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == confirm) {
                Tables table = tableTable.getSelectionModel().getSelectedItem();
                tableTable.getItems().remove(table);
                Request.deleteTable(table.getId());

            }
            return;
        }
        if(userCategory != null){
            for(User user : Request.getUsers()){
                if(user.getCategory() == userCategory.getId() || userCategory.getId() == User.getAdmin().getCategory()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("No se puede eliminar la categoría");
                    alert.setContentText("La categoría se encuentra en uso");
                    alert.showAndWait();
                    return;
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Deseas eliminar?");

            ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
            ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
            alert.getButtonTypes().setAll(confirm, cancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == confirm) {
                UserCategory category = userCatTable.getSelectionModel().getSelectedItem();
                userCatTable.getItems().remove(category);
                Request.deleteTable(category.getId());
            }
            return;
        }
        if(productCategory != null){
            for(Product p : Request.getProducts()){
                if(p.getCategory() == productCategory.getId()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("No se puede eliminar la categoría");
                    alert.setContentText("La categoría se encuentra en uso");
                    alert.showAndWait();
                    return;
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Deseas eliminar el usuario?");

            ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
            ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
            alert.getButtonTypes().setAll(confirm, cancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == confirm) {
                Request.deleteProduct(productCategory.getId());
                prodCatTable.getItems().remove(productCategory);
            }
        }
    }

    @FXML
    void editObject(ActionEvent event) {
        if(tableTable.getSelectionModel().getSelectedItem() != null){
            GeneralQueue.tablesQueue.add(tableTable.getSelectionModel().getSelectedItem());
            Views.switchView(Views.EDIT_TABLE_VIEW);
            return;
        }
        if(userCatTable.getSelectionModel().getSelectedItem() != null){
            GeneralQueue.userCategoryQueue.add(userCatTable.getSelectionModel().getSelectedItem());
            Views.switchView(Views.EDIT_USER_CATEGORY_VIEW);
            return;
        }
        if(prodCatTable.getSelectionModel().getSelectedItem() != null){
            GeneralQueue.productCategoryQueue.add(prodCatTable.getSelectionModel().getSelectedItem());
            Views.switchView(Views.EDIT_PRODUCT_CATEGORY_VIEW);
        }
    }

    @FXML
    void newProdCat(ActionEvent event) {
        Views.switchView(Views.NEW_PRODUCT_CATEGORY_VIEW);
    }

    @FXML
    void newTable(ActionEvent event) {
        Views.switchView(Views.NEW_TABLE_VIEW);
    }

    @FXML
    void newUserCat(ActionEvent event) {
        Views.switchView(Views.NEW_USER_CATEGORY_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos las tablas y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        capCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        idProdCat.setCellValueFactory(new PropertyValueFactory<>("id"));
        idTab.setCellValueFactory(new PropertyValueFactory<>("id"));
        idUserCat.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodCatName.setCellValueFactory(new PropertyValueFactory<>("name"));
        userCatName.setCellValueFactory(new PropertyValueFactory<>("name"));

        userCatTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!= null) {
                // Si hay una nueva selección en la tabla actual, deselecciona en las otras
                tableTable.getSelectionModel().clearSelection();
                prodCatTable.getSelectionModel().clearSelection();
            }
        });
        tableTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!= null) {
                // Si hay una nueva selección en la tabla actual, deselecciona en las otras
                userCatTable.getSelectionModel().clearSelection();
                prodCatTable.getSelectionModel().clearSelection();
            }
        });
        prodCatTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!= null) {
                // Si hay una nueva selección en la tabla actual, deselecciona en las otras
                userCatTable.getSelectionModel().clearSelection();
                tableTable.getSelectionModel().clearSelection();
            }
        });

        catsView.showingProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                AppManager manager = AppManager.getInstance();
                AppBar appBar = manager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        manager.getDrawer().open()));
                appBar.setTitleText("Productos");

                prodCatTable.getItems().clear();
                prodCatTable.getItems().addAll(Request.getProductCategories());

                userCatTable.getItems().clear();
                userCatTable.getItems().addAll(Request.getUserCategories());

                tableTable.getItems().clear();
                tableTable.getItems().addAll(Request.getTables());

                Window window = manager.getView().getScene().getWindow();
                window.setHeight(catsView.getPrefHeight() + 100);
                window.setWidth(catsView.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }
}
