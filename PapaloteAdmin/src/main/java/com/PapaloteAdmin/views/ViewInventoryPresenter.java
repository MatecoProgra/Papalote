package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.GeneralQueue;
import com.PapaloteAdmin.classes.Inventory;
import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.data.InventoryData;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ViewInventoryPresenter {

    @FXML
    private TableColumn<InventoryData, Integer> idCol;

    @FXML
    private TableColumn<InventoryData, String> lastCol;

    @FXML
    private TableColumn<InventoryData, Integer> maxCol;

    @FXML
    private TableColumn<InventoryData, Integer> minCol;

    @FXML
    private TableColumn<InventoryData, String> prodCol;

    @FXML
    private TableColumn<InventoryData, Integer> quantCol;

    @FXML
    private TableView<InventoryData> prodsTable;

    @FXML
    private View viewInventory;

    @FXML
    void backAction(ActionEvent event) {
        Views.switchView(Views.MAIN_VIEW);
    }
    /**
     * @param event No utilizado
     * @@apiNote Guardamos el producto de inventario en la cola y redirigimos
     */
    @FXML
    void editSelectedUser(ActionEvent event) {
        GeneralQueue.inventoryQueue.add(prodsTable.getSelectionModel().getSelectedItem());
        Views.switchView(Views.EDIT_STOCK_VIEW);
    }
    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos las tablas y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastCol.setCellValueFactory(new PropertyValueFactory<>("last_upd"));
        maxCol.setCellValueFactory(new PropertyValueFactory<>("max_stock"));
        minCol.setCellValueFactory(new PropertyValueFactory<>("min_stock"));
        prodCol.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        quantCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        viewInventory.showingProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                AppManager manager = AppManager.getInstance();
                AppBar appBar = manager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        manager.getDrawer().open()));
                appBar.setTitleText("Inventario");

                prodsTable.getItems().clear();
                ObservableList<Inventory> inv = Request.getInventory();

                for (Inventory inventory : inv) {
                    InventoryData inventoryData = new InventoryData();
                    inventoryData.setId(inventory.getId());
                    inventoryData.setId_product(inventory.getId_product());
                    inventoryData.setQuantity(inventory.getQuantity());
                    inventoryData.setMax_stock(inventory.getMax_stock());
                    inventoryData.setMin_stock(inventory.getMin_stock());
                    inventoryData.setLast_update(inventory.getLast_update());
                    inventoryData.setLast_upd(inventory.getLast_update().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    inventoryData.setProduct_name(Objects.requireNonNull(Request.getProduct(inventoryData.getId_product())).getName());
                    prodsTable.getItems().add(inventoryData);
                }

                Window window = manager.getView().getScene().getWindow();
                window.setHeight(viewInventory.getPrefHeight() + 100);
                window.setWidth(viewInventory.getPrefWidth()+25);
                window.centerOnScreen();
            }
        });
    }

}
