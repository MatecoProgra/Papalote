package com.PapaloteAdmin.views;

import com.PapaloteAdmin.classes.*;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.stage.Window;

public class AddProducPresenter {

    private Product product;

    @FXML
    private View createOrder;

    @FXML
    private Spinner<Integer> quantPicker;

    @FXML
    private TextArea comments;

    /**
     * @param event No se utiliza
     *
     * @@apiNote Una vez agregada la informacion del producto en la orden, lo eliminamos de la cola de modificacion
     * y lo enviamos
     */
    @FXML
    void OkButtonPress(ActionEvent event) {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(NewOrderPresenter.presenter.getSelectedOrder().getId());
        orderInfo.setProductId(product.getId());
        orderInfo.setQuantity(quantPicker.getValue());
        orderInfo.setComments(comments.getText());
        orderInfo.setState(OrderInfo.PENDIENT);
        Request.addToOrder(orderInfo);
        GeneralQueue.selectedProductQueue.remove(0);
        Views.switchView(Views.NEW_ORDER_VIEW);
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        GeneralQueue.selectedProductQueue.remove(0);
        Views.switchView(Views.NEW_ORDER_VIEW);
    }

    /**
     * @@apiNote Al inicializar, agregamos el titulo a la appbar y cambiar los valores maximos, minimos y actual del spinner
     */
    public void initialize() {
        createOrder.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Realizar orden");

                this.product = GeneralQueue.selectedProductQueue.get(0);
                for(Inventory inv: Request.getInventory()){
                    if(inv.getId_product()==product.getId()){
                        quantPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,inv.getQuantity(),1));

                    }
                }

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(createOrder.getPrefHeight() + 100);
                window.setWidth(createOrder.getPrefWidth() + 20);
                window.centerOnScreen();
            }
        });
    }

}
