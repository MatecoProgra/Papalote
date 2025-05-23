package com.PapaloteAdmin.views;


import com.PapaloteAdmin.classes.*;

import com.PapaloteAdmin.classes.data.OrderInfoData;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.util.Optional;

public class EditOrderInfoPresenter {

    @FXML
    private TableColumn<OrderInfoData, String> commentsCol;

    @FXML
    private View editOrder;

    @FXML
    private TableView<OrderInfoData> orderTable;

    @FXML
    private TableColumn<OrderInfoData, String> productCol;

    @FXML
    private TableColumn<OrderInfoData, Integer> quantCol;

    @FXML
    private TableColumn<OrderInfoData, String> stateCol;

    @FXML
    void backAction(ActionEvent event) {
        Views.switchView(Views.NEW_ORDER_VIEW);
    }

    /**
     * @param event No utilizado
     * @@apiNote Si seleccionamos un item, confirmamos con el alert y eliminamos
     */
    @FXML
    void deleteSelectedItem(ActionEvent event) {
        OrderInfoData selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Deseas terminar la orden?");
            ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
            ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
            alert.getButtonTypes().setAll(confirm, cancel);
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == confirm){
                Request.deleteOrderInfo(selectedOrder.getId());
                orderTable.getItems().remove(selectedOrder);
            }
        }
    }

    /**
     * @param event No utilizado
     * @@apiNote Guardamos la informacion del item en la cola y cambiamos la vista a la edicion
     */
    @FXML
    void editSelectedItem(ActionEvent event) {
        GeneralQueue.orderInfoQueue.add(orderTable.getSelectionModel().getSelectedItem());
        Views.switchView(Views.EDIT_ORDER_INFO_VIEW);
    }


    /**
     * @param event No utilizado
     * @@apiNote No utilizado
     */
    @FXML
    void sendCommand(ActionEvent event) {
        NotImplemented.notImplemented();
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos la tabla y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        commentsCol.setCellValueFactory(new PropertyValueFactory<>("comments"));
        productCol.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        quantCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));

        editOrder.showingProperty().addListener((obs, oldV, newV)->{
            if(newV){
                AppManager manager = AppManager.getInstance();
                AppBar appBar = manager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Editar orden");

                Order order = GeneralQueue.ordersQueue.get(0);
                orderTable.getItems().clear();
                ObservableList<OrderInfo> orders = Request.getOrderInfoById(order.getId());

                if(!orders.isEmpty()){
                    for (OrderInfo info : orders) {
                        OrderInfoData orderInfoData = new OrderInfoData();
                        orderInfoData.setOrderId(order.getId());
                        orderInfoData.setId(info.getId());
                        orderInfoData.setComments(info.getComments());
                        orderInfoData.setQuantity(info.getQuantity());
                        orderInfoData.setState(info.getState());
                        orderInfoData.setProduct_name(Request.getProduct(info.getProductId()).getName());
                        orders.add(orderInfoData);
                        orderTable.getItems().add(orderInfoData);
                    }
                }else
                    System.out.println("No se encontraron ordenes");

                Window window = manager.getView().getScene().getWindow();
                window.setHeight(editOrder.getPrefHeight() + 100);
                window.setWidth(editOrder.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }
}
