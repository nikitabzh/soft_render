module org.example.soft_render {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.soft_render to javafx.fxml;
    exports org.example.soft_render;
}