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

public class EditProdInOrderPresenter {
    private OrderInfo orderInfo;
    private Product product;

    @FXML
    private View createOrder;

    @FXML
    private Spinner<Integer> quantPicker;

    @FXML
    private TextArea comments;

    /**
     * @param event No utilizado
     * @@apiNote Actualizamos la informacion del item en la orden y la eliminamos de la cola
     */
    @FXML
    void OkButtonPress(ActionEvent event) {

        orderInfo.setOrderId(NewOrderPresenter.presenter.getSelectedOrder().getId());
        orderInfo.setProductId(product.getId());
        orderInfo.setQuantity(quantPicker.getValue());
        orderInfo.setComments(comments.getText());
        orderInfo.setState(OrderInfo.PENDIENT);
        Request.editOrderInfo(orderInfo);
        GeneralQueue.orderInfoQueue.remove(0);
        Views.switchView(Views.CREATE_ORDER_VIEW);

    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        GeneralQueue.orderInfoQueue.remove(0);
        Views.switchView(Views.CREATE_ORDER_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos con la informacion del item y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        createOrder.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Realizar orden");

                orderInfo = GeneralQueue.orderInfoQueue.get(0);
                product = Request.getProduct(orderInfo.getProductId());

                for(Inventory inv: Request.getInventory()){
                    if(inv.getId_product()==product.getId()){
                        quantPicker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,inv.getQuantity(),1));
                    }
                }
                quantPicker.getValueFactory().setValue(orderInfo.getQuantity());
                comments.setText(orderInfo.getComments());

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(createOrder.getPrefHeight() + 100);
                window.setWidth(createOrder.getPrefWidth() + 20);
                window.centerOnScreen();
            }
        });
    }

}
