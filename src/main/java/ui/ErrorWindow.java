// ui/ErrorWindow.java
package ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ErrorWindow {
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred!");
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
    }
}