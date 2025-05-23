package com.PapaloteAdmin.classes;

import javafx.scene.control.Alert;

public class NotImplemented {
    public static void notImplemented(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Not Implemented");
        alert.setHeaderText("Not Implemented");
        alert.setContentText("This operation is not implemented");
        alert.showAndWait();
    }
}
