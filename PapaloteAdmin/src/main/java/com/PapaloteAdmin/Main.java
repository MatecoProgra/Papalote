package com.PapaloteAdmin;

import com.PapaloteAdmin.classes.Request;
import com.PapaloteAdmin.classes.User;
import com.PapaloteAdmin.classes.UserCategory;
import com.PapaloteAdmin.views.Views;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.charm.glisten.visual.Theme;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private final AppManager appManager = AppManager.initialize(this::postInit);

    private static User user = new User();

    /**
     * Inicializamos las vistas y el appmanager
     */
    @Override
    public void init() {
        Views views = new Views();
        views.initialize(appManager);
    }

    /**
     * @param primaryStage Nuestra ventana
     * @@apiNote Iniciamos creando usuario de admin y su categoria y si no se pudo, cerramos la app
     */
    @Override
    public void start(Stage primaryStage) {
        try{
            primaryStage.setResizable(false);
            if (Request.getUserCategories().isEmpty()) {
                UserCategory userCategory = new UserCategory();
                userCategory.setName("Admin");
                userCategory.setAdmin(true);
                Request.addUserCategory(userCategory);
            }
            if (Request.getUsers().isEmpty()) {
                user = User.getAdmin();
            }
            if (Request.getUserCategories().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo conectar al servidor");
                alert.showAndWait();
                return;
            }
            appManager.start(primaryStage);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Papalote Admin");
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    /**
     * @param scene Escena mostrada
     *
     * @@apiNote Cambiamos el tema y agregamos el icono a la ventana
     */
    private void postInit(Scene scene) {
        Swatch.RED.assignTo(scene);
        Theme.DARK.assignTo(scene);
        ((Stage) scene.getWindow()).getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icon.png"))));
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Main.user = user;
    }
}
