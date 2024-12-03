package org.example.soft_render;

import javafx.scene.control.Alert;

public class InterfaceManager {

    public void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // добавить методы для управления интерфейсом
}
