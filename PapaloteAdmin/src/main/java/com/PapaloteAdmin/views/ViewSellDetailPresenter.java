package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.SellDetail;
import com.PapaloteAdmin.classes.data.SellDetailData;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.util.Objects;

public class ViewSellDetailPresenter {
    @FXML
    private TableColumn<SellDetailData, Integer> idCol;

    @FXML
    private TableColumn<SellDetailData, String> productCol;

    @FXML
    private TableColumn<SellDetailData, Integer> quantCol;

    @FXML
    private TableColumn<SellDetailData, Integer> sellCol;

    @FXML
    private TableView<SellDetailData> sellDetailTable;

    @FXML
    private TableColumn<SellDetailData, Double> subtotalCol;

    @FXML
    private TableColumn<SellDetailData, Double> unitCol;

    @FXML
    private View viewSellsDet;

    @FXML
    void backAction(ActionEvent event) {
        Views.switchView(Views.MAIN_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos las tablas y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productCol.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        quantCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sellCol.setCellValueFactory(new PropertyValueFactory<>("id_sell"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        viewSellsDet.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppManager manager = AppManager.getInstance();
                AppBar appBar = manager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        manager.getDrawer().open()));
                appBar.setTitleText("Detalle de ventas");

                sellDetailTable.getItems().clear();
                ObservableList<SellDetailData> sellsData = FXCollections.observableArrayList();
                for(SellDetail sell: Request.getSellsDetails()){
                    SellDetailData data = new SellDetailData();
                    data.setId(sell.getId());
                    data.setId_sell(sell.getId());
                    data.setQuantity(sell.getQuantity());
                    data.setSubtotal(sell.getSubtotal());
                    data.setUnit_price(sell.getUnit_price());
                    data.setProduct_name(Objects.requireNonNull(Request.getProduct(data.getId_product())).getName());
                    sellsData.add(data);
                }
                sellDetailTable.getItems().addAll(sellsData);
                Window window = manager.getView().getScene().getWindow();
                window.setHeight(viewSellsDet.getPrefHeight() + 100);
                window.setWidth(viewSellsDet.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }

}
