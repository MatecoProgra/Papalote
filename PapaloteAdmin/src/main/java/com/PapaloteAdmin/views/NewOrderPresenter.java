package com.PapaloteAdmin.views;

import com.PapaloteAdmin.Main;
import com.PapaloteAdmin.classes.*;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Math.floorDiv;

public class NewOrderPresenter {

    public static NewOrderPresenter presenter;

    @FXML
    private Button cancelButton;

    @FXML
    private TabPane catsTabPane;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button finishButton;

    @FXML
    private View newOrder;

    @FXML
    private Pane ordsPane;

    private Order selectedOrder;

    @FXML
    void backAction(ActionEvent event) {
        Views.switchView(Views.MAIN_VIEW);
    }

    /**
     * @param event No utilizado
     * @@apiNote Desactivamos los botones y deseleccionamos la orden
     */
    @FXML
    void cancelAction(ActionEvent event) {
        cancelButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        finishButton.setDisable(true);
        selectedOrder = null;
    }


    /**
     * @param event No utilizado
     * @@apiNote Validamos que exista una orden seleccionada, confirmamos con el alert y eliminamos
     */
    @FXML
    void deleteAction(ActionEvent event) {
        if(selectedOrder!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Deseas eliminar?");

            ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
            ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
            alert.getButtonTypes().setAll(confirm, cancel);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == confirm){
                ordsPane.getChildren().remove(ordsPane);
                Request.deleteOrder(selectedOrder.getId());
                cancelButton.setDisable(true);
                deleteButton.setDisable(true);
                editButton.setDisable(true);
                finishButton.setDisable(true);
                selectedOrder = null;
                fillOrders();
            }
        }
    }
    /**
     * @param event No utilizado
     * @@apiNote Validamos que exista una orden seleccionada y redirigimos a la edicion, agregando la orden en la cola
     */
    @FXML
    void editAction(ActionEvent event) {
        if(selectedOrder!=null){
            GeneralQueue.ordersQueue.add(selectedOrder);
            Views.switchView(Views.EDIT_ORDER_VIEW);
        }
    }

    /**
     * @param event No utilizado
     * @@apiNote Validamos que exista una orden seleccionada, confirmamos con el alert y terminamos la venta
     */
    @FXML
    void finishAction(ActionEvent event) {
        if(selectedOrder!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Deseas terminar la orden?");

            ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
            ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
            alert.getButtonTypes().setAll(confirm, cancel);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == confirm){
                ordsPane.getChildren().remove(ordsPane);
                Request.completeSell(selectedOrder.getId());

                cancelButton.setDisable(true);
                deleteButton.setDisable(true);
                editButton.setDisable(true);
                finishButton.setDisable(true);
                selectedOrder = null;
                fillOrders();
            }
        }
    }
    //Creamos la orden
    @FXML
    void newOrderAction(ActionEvent event) {
        Views.switchView(Views.CREATE_ORDER_VIEW);
    }

    /**
     * @@apiNote Cambiamos el titulo en la appbar, rellenamos con las ordenes y productos y cambiamos las dimensiones de la pantalla
     */
    public void initialize() {
        newOrder.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                presenter = this;
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Realizar orden");

                //Por cada categoria de producto se agrega una nueba tab
                catsTabPane.getTabs().clear();
                for(ProductCategory pc: Request.getProductCategories()){
                    ObservableList<Product> prods = Request.getProductsByCategory(pc.getId());
                    if(!prods.isEmpty()){
                        Tab tab = new Tab();
                        tab.setText(pc.getName());
                        tab.setClosable(false);
                        ScrollPane scrollPane = new ScrollPane();
                        scrollPane.setFitToWidth(true);
                        scrollPane.setContent(fillCat(pc));
                        tab.setContent(scrollPane);
                        catsTabPane.getTabs().add(tab);
                    }
                }
                //Rellenamos las ordenes
                fillOrders();

                Window window = AppManager.getInstance().getView().getScene().getWindow();
                window.setHeight(newOrder.getPrefHeight() + 100);
                window.setWidth(newOrder.getPrefWidth());
                window.centerOnScreen();
            }
        });
    }

    /**
     * @param product El producto a ser agregado a la orden
     *
     * @@apiNote Agregamos el producto a la orderInfo
     */
    public void addProduct(Product product){
        if(selectedOrder==null && !Request.getOrders().isEmpty()){
            return;
        }
        //Si no existen ordenes, creamos una nueva y enviamos
        else if(Request.getOrders().isEmpty()){
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Crear orden");
            dialog.setHeaderText("Seleccione una mesa");

            ChoiceBox<Integer> tableBox = new ChoiceBox<>();
            for(Tables t: Request.getTables()){
                tableBox.getItems().add(t.getId());
            }
            tableBox.setValue(tableBox.getItems().get(0));
            //Selecionamos la mesa
            dialog.getDialogPane().setContent(tableBox);
            ButtonType confirm = new ButtonType("Si", ButtonType.YES.getButtonData());
            ButtonType cancel = new ButtonType("No", ButtonType.NO.getButtonData());
            dialog.addEventHandler(DialogEvent.DIALOG_HIDDEN, event -> {
                //Si se cierra la ventana emergente, se crea una nueva orden
                if(tableBox.getValue()!=null){
                    Order order = new Order();
                    order.setTableId(tableBox.getValue());
                    order.setOrderDate(LocalDateTime.now());
                    order.setEmployeeId(Main.getUser().getId());
                    order.setOrderStatus(Order.ON_PROCESS);
                    this.selectedOrder = order;
                    Request.addOrder(order);
                }
            });
            dialog.getDialogPane().getButtonTypes().setAll(confirm, cancel);
            dialog.showAndWait();
        }
        GeneralQueue.selectedProductQueue.add(product);
        Views.switchView(Views.ADD_PRODUCT_VIEW);
    }

    /**
     * @param order La orden a seleccionar
     *
     * @@apiNote Habilitamos los botones y seleccionamos la orden
     */
    void selectOrder(OrderPane order){
        cancelButton.setDisable(false);
        deleteButton.setDisable(false);
        editButton.setDisable(false);
        finishButton.setDisable(false);
        this.selectedOrder = order.getOrder();
    }

    /**
     * @param cat Categoría para hacer panel
     * @return Devuelve el panel con todos los productos de la categoria
     */
    private Pane fillCat(ProductCategory cat){
        Pane pane = new Pane();
        pane.setPrefHeight(550);
        pane.setPrefWidth(820);
        pane.setStyle("-fx-background-color:  #4a4a4a");
        ObservableList<Product> cats = Request.getProductsByCategory(cat.getId());
        int i = 0;

        //Por todos los productos de la categoria, creamos un panel que se mostrará abajo
        for(Product p: cats){
            ProductPane productPane = new ProductPane(p,i++);
            pane.getChildren().add(productPane);
            //Aumentamos el tamaño de ser necesario
            if (productPane.getPosy()>pane.getHeight())
                pane.setPrefHeight(productPane.getHeight() + productPane.getPosy());

        }
        return pane;
    }


    /**
     * @@apiNote Rellena un panel con cada una de las ordenes activas actualmente
     */
    private void fillOrders(){
        ordsPane.getChildren().clear();
        int i = 0;
        for(Order ord: Request.getOrders()){
            OrderPane orderPane = new OrderPane(ord,i++);
            if(orderPane.posy> orderPane.getHeight()){
                ordsPane.setPrefHeight(orderPane.posy + orderPane.getHeight());
            }
            ordsPane.getChildren().add(orderPane);
        }
    }

    /**
     * @return Devuelve la orden seleccionada
     */
    public Order getSelectedOrder(){
        return selectedOrder;
    }
}

/**
 * Clase utilizada para mostrar cada uno de los productos
 */
class ProductPane extends Pane{
    private int posx = 200;
    private int posy = 200;

    public ProductPane(Product p, int index){
        //Editamos las dimensiones y la posicion
        posx = (posx * (index % 4)); //El maximo por fila son 4
        posy = floorDiv(index,4) * posy;
        this.setWidth(120);
        this.setHeight(120);
        this.setLayoutX(posx);
        this.setLayoutY(posy);
        this.setStyle("-fx-background-color:  #323232;-fx-background-radius: 30px");

        //Colocmos el nombre y lo centramos
        Label name = new Label();
        name.setText(p.getName());
        name.setStyle("-fx-font-size: 18; -fx-background-color: #323232; -fx-text-fill: white;-fx-background-radius: 30px");
        name.setPrefHeight(120);
        name.setPrefWidth(120);
        name.setLayoutX(0);
        name.setLayoutY(0);
        name.setAlignment(Pos.CENTER);
        name.setTextAlignment(TextAlignment.CENTER);
        name.setContentDisplay(ContentDisplay.CENTER);
        name.setWrapText(true);

        Button add = new Button();
        add.setPrefHeight(120);
        add.setPrefWidth(120);
        add.setStyle("-fx-background-color: #ffffff00");
        add.setOnAction((ActionEvent event) -> {
            NewOrderPresenter.presenter.addProduct(p);
        });
        this.getChildren().addAll(name, add);
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }
}

/**
 * Clase utilizada para mostrar las ordenes como iconos
 */
class OrderPane extends Pane{
    int posy = 180;
    private final Order order;

    public OrderPane(Order order, int index){
        //Editamos las dimensiones y la posicion
        this.order = order;
        posy = index * posy;
        this.setWidth(180);
        this.setHeight(180);
        this.setLayoutX(10);
        this.setLayoutY(posy);
        this.setStyle("-fx-background-color:  #565656;-fx-background-radius: 30px");

        //Agregamos el numero de orden y lo posicionamos
        Label name = new Label();
        name.setText(String.valueOf(order.getId()));
        name.setStyle("-fx-font-size: 18; -fx-background-color: #565656; -fx-text-fill: white;-fx-background-radius: 30px");
        name.setLayoutX((180-name.getWidth())/2);
        name.setLayoutY(40);
        name.setAlignment(Pos.CENTER);
        name.setTextAlignment(TextAlignment.CENTER);
        name.setContentDisplay(ContentDisplay.CENTER);
        name.setWrapText(true);

        //Agregamos un boton invisible
        Button add = new Button();
        add.setPrefHeight(120);
        add.setPrefWidth(125);
        add.setStyle("-fx-background-color: #ffffff00");
        add.setOnAction((ActionEvent event) -> {
            NewOrderPresenter.presenter.selectOrder(this);
        });
        this.getChildren().addAll(name, add);
    }

    /**
     * @return Devuelve la orden asigada a el panel
     */
    public Order getOrder(){
        return order;
    }
}
