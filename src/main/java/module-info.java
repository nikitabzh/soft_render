module main.soft_render {
    requires javafx.controls;
    requires javafx.fxml;

    opens ui to javafx.fxml;
    exports ui;
}