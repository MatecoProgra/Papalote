package com.PapaloteAdmin.views;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

public class Views {

    public static final String LOGIN_VIEW = HOME_VIEW;
    public static final String MAIN_VIEW = "Main View";

    public static final String VIEW_USERS_VIEW = "View Users View";
    public static final String VIEW_SELLS_VIEW = "View Sells View";
    public static final String VIEW_SELL_DETAILS_VIEW = "View Sells Details View";
    public static final String VIEW_PRODS_VIEW = "View Prods View";
    public static final String VIEW_ORDER = "View order View";
    public static final String VIEW_INVENTORY_VIEW = "View Inventory View";
    public static final String VIEW_CATS_VIEW = "View Cats View";

    public static final String NEW_USER_VIEW = "New User View";
    public static final String NEW_USER_CATEGORY_VIEW = "New User Categroy View";
    public static final String NEW_TABLE_VIEW = "New Table View";
    public static final String NEW_PRODUCT_VIEW = "New Product View";
    public static final String NEW_PRODUCT_CATEGORY_VIEW = "New Product Categroy View";
    public static final String NEW_ORDER_VIEW = "New Order View";

    public static final String EDIT_USER_VIEW = "Edit User View";
    public static final String EDIT_USER_CATEGORY_VIEW = "Edit User Category View";
    public static final String EDIT_ORDER_VIEW = "Edit Order View";
    public static final String EDIT_ORDER_INFO_VIEW = "Edit Order INFO View";
    public static final String EDIT_PRODUCT_VIEW = "Edit Product View";
    public static final String EDIT_PRODUCT_CATEGORY_VIEW = "Edit Product Category View";
    public static final String EDIT_TABLE_VIEW = "Edit Table View";
    public static final String EDIT_STOCK_VIEW = "Edit Stock View";

    public static final String CREATE_ORDER_VIEW = "Create Order View";
    public static final String ADD_PRODUCT_VIEW = "Add Product View";

    /**
     * @param name Nombre de la vista que necesitamos
     * @return Devuelve la vista obtenida dependiendo del nombre
     */
    private View getView(String name){
        View view = new View();
        try{
            switch (name) {
                case LOGIN_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
                    break;
                case MAIN_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
                    break;
                case VIEW_USERS_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewUsers.fxml")));
                    break;
                case VIEW_SELLS_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewSells.fxml")));
                    break;
                case VIEW_SELL_DETAILS_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewSellDetail.fxml")));
                    break;
                case VIEW_PRODS_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewProducts.fxml")));
                    break;
                case VIEW_ORDER:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewOrder.fxml")));
                    break;
                case VIEW_INVENTORY_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewInventory.fxml")));
                    break;
                case VIEW_CATS_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewCategories.fxml")));
                    break;
                case NEW_USER_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newUser.fxml")));
                    break;
                case NEW_USER_CATEGORY_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newUserCategory.fxml")));
                    break;
                case NEW_ORDER_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newOrder.fxml")));
                    break;
                case NEW_PRODUCT_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newProduct.fxml")));
                    break;
                case NEW_PRODUCT_CATEGORY_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newProductCategory.fxml")));
                    break;
                case NEW_TABLE_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newTable.fxml")));
                    break;
                case EDIT_USER_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editUser.fxml")));
                    break;
                case EDIT_USER_CATEGORY_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editUserCat.fxml")));
                    break;
                case EDIT_ORDER_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editOrderInfo.fxml")));
                    break;
                case EDIT_ORDER_INFO_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editProdInOrder.fxml")));
                    break;
                case EDIT_PRODUCT_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editProduct.fxml")));
                    break;
                case EDIT_PRODUCT_CATEGORY_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editProductCat.fxml")));
                    break;
                case EDIT_TABLE_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editTable.fxml")));
                    break;
                case EDIT_STOCK_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editStock.fxml")));
                    break;
                case CREATE_ORDER_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("createOrder.fxml")));
                    break;
                case ADD_PRODUCT_VIEW:
                    view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addProduct.fxml")));
            }
        }catch (IOException e){
            System.out.println("IOException: " + e);
            ((Stage)view.getScene().getWindow()).setTitle("Error");
            return view;
        }

        return view;
    }

    /**
     * @param view El nombre de la vista que vamos a cambiar
     */
    public static void switchView(String view){
        AppManager.getInstance().switchView(view);
    }


    /**
     * @param appManager El manager de nuestro proyecto
     *
     * @@apiNote Agregamos cada una de las vistas a nuestro appManager
     */
    public void initialize(AppManager appManager) {
        appManager.addViewFactory(LOGIN_VIEW,()-> getView(LOGIN_VIEW));
        appManager.addViewFactory(MAIN_VIEW,()-> getView(MAIN_VIEW));

        appManager.addViewFactory(VIEW_USERS_VIEW,()-> getView(VIEW_USERS_VIEW));
        appManager.addViewFactory(VIEW_SELLS_VIEW,()-> getView(VIEW_SELLS_VIEW));
        appManager.addViewFactory(VIEW_SELL_DETAILS_VIEW,()-> getView(VIEW_SELL_DETAILS_VIEW));
        appManager.addViewFactory(VIEW_PRODS_VIEW,()-> getView(VIEW_PRODS_VIEW));
        appManager.addViewFactory(VIEW_ORDER,()-> getView(VIEW_ORDER));
        appManager.addViewFactory(VIEW_INVENTORY_VIEW,()-> getView(VIEW_INVENTORY_VIEW));
        appManager.addViewFactory(VIEW_CATS_VIEW,()-> getView(VIEW_CATS_VIEW));

        appManager.addViewFactory(NEW_USER_VIEW,()-> getView(NEW_USER_VIEW));
        appManager.addViewFactory(NEW_USER_CATEGORY_VIEW,()-> getView(NEW_USER_CATEGORY_VIEW));
        appManager.addViewFactory(NEW_ORDER_VIEW,()-> getView(NEW_ORDER_VIEW));
        appManager.addViewFactory(NEW_PRODUCT_VIEW,()-> getView(NEW_PRODUCT_VIEW));
        appManager.addViewFactory(NEW_PRODUCT_CATEGORY_VIEW,()-> getView(NEW_PRODUCT_CATEGORY_VIEW));
        appManager.addViewFactory(NEW_TABLE_VIEW,()-> getView(NEW_TABLE_VIEW));

        appManager.addViewFactory(EDIT_USER_VIEW,()-> getView(EDIT_USER_VIEW));
        appManager.addViewFactory(EDIT_USER_CATEGORY_VIEW,()-> getView(EDIT_USER_CATEGORY_VIEW));
        appManager.addViewFactory(EDIT_PRODUCT_VIEW,()-> getView(EDIT_PRODUCT_VIEW));
        appManager.addViewFactory(EDIT_PRODUCT_CATEGORY_VIEW,()-> getView(EDIT_PRODUCT_CATEGORY_VIEW));
        appManager.addViewFactory(EDIT_ORDER_VIEW,()-> getView(EDIT_ORDER_VIEW));
        appManager.addViewFactory(EDIT_ORDER_INFO_VIEW,()-> getView(EDIT_ORDER_INFO_VIEW));
        appManager.addViewFactory(EDIT_TABLE_VIEW,()-> getView(EDIT_TABLE_VIEW));
        appManager.addViewFactory(EDIT_STOCK_VIEW,()-> getView(EDIT_STOCK_VIEW));

        appManager.addViewFactory(CREATE_ORDER_VIEW,()-> getView(CREATE_ORDER_VIEW));
        appManager.addViewFactory(ADD_PRODUCT_VIEW,()-> getView(ADD_PRODUCT_VIEW));
    }
}

