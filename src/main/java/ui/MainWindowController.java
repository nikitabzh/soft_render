package ui;

import core.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import math.Matrix4;
import math.Vector3;
import render.Renderer;
import render.Texture;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainWindowController {

    @FXML
    private Canvas canvas;

    @FXML
    private ListView<Model> modelListView;

    @FXML
    private Button deleteVertexButton;

    @FXML
    private Button deletePolygonButton;
    @FXML
    private VBox transformationPane;
    @FXML
    private CheckBox lightingCheckBox;
    @FXML
    private CheckBox textureCheckBox;

    @FXML
    private TextField translateX;
    @FXML
    private TextField translateY;
    @FXML
    private TextField translateZ;
    @FXML
    private Button translateUpX;
    @FXML
    private Button translateDownX;
    @FXML
    private Button translateUpY;
    @FXML
    private Button translateDownY;
    @FXML
    private Button translateUpZ;
    @FXML
    private Button translateDownZ;


    @FXML
    private TextField rotateX;
    @FXML
    private TextField rotateY;
    @FXML
    private TextField rotateZ;
    @FXML
    private Button rotateUpX;
    @FXML
    private Button rotateDownX;
    @FXML
    private Button rotateUpY;
    @FXML
    private Button rotateDownY;
    @FXML
    private Button rotateUpZ;
    @FXML
    private Button rotateDownZ;


    @FXML
    private TextField scaleX;
    @FXML
    private TextField scaleY;
    @FXML
    private TextField scaleZ;
     @FXML
    private Button scaleUpX;
    @FXML
    private Button scaleDownX;
    @FXML
    private Button scaleUpY;
    @FXML
    private Button scaleDownY;
    @FXML
    private Button scaleUpZ;
    @FXML
    private Button scaleDownZ;

    private Scene scene;
    private Camera camera;
    private boolean isShiftPressed;
    private double mouseX, mouseY;
    private Renderer renderer;
    private boolean lightingEnabled = false;
    private boolean textureEnabled = false;
    private Vector3 cameraRotation = new Vector3(0, 0, 0);
    private Vector3 cameraOffset = new Vector3(0, 0, 0);
    private double cameraTranslateSpeed = 0.1;
    private Vector3 orbitTarget = new Vector3(0, 0, 0);
    private boolean isOrbiting = false;
    private double cameraDistance = 5;
    private double rotateSensitivity = 0.01;
    @FXML
    public void initialize() {
        scene = new Scene();
        camera = new Camera();

        modelListView.setItems(FXCollections.observableList(scene.getModels()));
        modelListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        modelListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateSelection();
        });

        deleteVertexButton.setOnAction(e -> deleteSelectedVertices());
        deletePolygonButton.setOnAction(e -> deleteSelectedPolygons());
        transformationPane.setVisible(true);

        translateX.textProperty().addListener((observable, oldValue, newValue) -> updateModelTransform());
        translateY.textProperty().addListener((observable, oldValue, newValue) -> updateModelTransform());
        translateZ.textProperty().addListener((observable, oldValue, newValue) -> updateModelTransform());

        rotateX.textProperty().addListener((observable, oldValue, newValue) -> updateModelTransform());
        rotateY.textProperty().addListener((observable, oldValue, newValue) -> updateModelTransform());
        rotateZ.textProperty().addListener((observable, oldValue, newValue) -> updateModelTransform());

        scaleX.textProperty().addListener((observable, oldValue, newValue) -> updateModelTransform());
        scaleY.textProperty().addListener((observable, oldValue, newValue) -> updateModelTransform());
        scaleZ.textProperty().addListener((observable, oldValue, newValue) -> updateModelTransform());

        lightingCheckBox.setOnAction(event -> {
            lightingEnabled = lightingCheckBox.isSelected();
            render();
        });
        textureCheckBox.setOnAction(event -> {
            textureEnabled = textureCheckBox.isSelected();
            render();
        });

        renderer = new Renderer(canvas);
        render();
        setupTransformationButtons();

        // Initialize fields with default values
        updateTransformFields();
    }

    private void setupTransformationButtons() {
        translateUpX.setOnAction(e -> incrementCoordinate(translateX, 1));
        translateDownX.setOnAction(e -> incrementCoordinate(translateX, -1));
        translateUpY.setOnAction(e -> incrementCoordinate(translateY, 1));
        translateDownY.setOnAction(e -> incrementCoordinate(translateY, -1));
        translateUpZ.setOnAction(e -> incrementCoordinate(translateZ, 1));
        translateDownZ.setOnAction(e -> incrementCoordinate(translateZ, -1));

        rotateUpX.setOnAction(e -> incrementCoordinate(rotateX, 1));
        rotateDownX.setOnAction(e -> incrementCoordinate(rotateX, -1));
        rotateUpY.setOnAction(e -> incrementCoordinate(rotateY, 1));
        rotateDownY.setOnAction(e -> incrementCoordinate(rotateY, -1));
        rotateUpZ.setOnAction(e -> incrementCoordinate(rotateZ, 1));
        rotateDownZ.setOnAction(e -> incrementCoordinate(rotateZ, -1));

         scaleUpX.setOnAction(e -> incrementCoordinate(scaleX, 0.1));
         scaleDownX.setOnAction(e -> incrementCoordinate(scaleX, -0.1));
         scaleUpY.setOnAction(e -> incrementCoordinate(scaleY, 0.1));
         scaleDownY.setOnAction(e -> incrementCoordinate(scaleY, -0.1));
         scaleUpZ.setOnAction(e -> incrementCoordinate(scaleZ, 0.1));
         scaleDownZ.setOnAction(e -> incrementCoordinate(scaleZ, -0.1));
    }

    private void incrementCoordinate(TextField textField, double increment) {
       try {
            double value = Double.parseDouble(textField.getText());
           textField.setText(String.format(Locale.US, "%.1f", value + increment));
        } catch (NumberFormatException e) {
           textField.setText(String.format(Locale.US, "%.1f",increment));
            ErrorWindow.showError("Неверный формат числа, используются значения по умолчанию");
        }
        updateModelTransform();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SHIFT) {
            isShiftPressed = true;
        }
        double moveSpeed = cameraTranslateSpeed;
        if(isShiftPressed){
            moveSpeed = moveSpeed * 3;
        }
        if(event.getCode() == KeyCode.W) {
            cameraOffset = cameraOffset.add(camera.getTarget().subtract(camera.getPosition()).normalize().multiply(moveSpeed));
            updateCameraPosition();
        }
        if(event.getCode() == KeyCode.S) {
            cameraOffset = cameraOffset.add(camera.getPosition().subtract(camera.getTarget()).normalize().multiply(moveSpeed));
            updateCameraPosition();
        }
        if(event.getCode() == KeyCode.A) {
            Vector3 view = camera.getPosition().subtract(camera.getTarget()).normalize();
            Vector3 right = view.crossProduct(camera.getUp()).normalize().multiply(moveSpeed);
            cameraOffset = cameraOffset.add(right);
            updateCameraPosition();
        }
        if(event.getCode() == KeyCode.D) {
            Vector3 view = camera.getPosition().subtract(camera.getTarget()).normalize();
            Vector3 right = camera.getUp().crossProduct(view).normalize().multiply(moveSpeed);
            cameraOffset = cameraOffset.add(right);
            updateCameraPosition();
        }
        if(event.getCode() == KeyCode.SPACE) {
            cameraOffset = cameraOffset.add(camera.getUp().normalize().multiply(moveSpeed));
            updateCameraPosition();
        }
        if(event.getCode() == KeyCode.CONTROL) {
            cameraOffset = cameraOffset.add(camera.getUp().negate().normalize().multiply(moveSpeed));
            updateCameraPosition();
        }
        if(event.getCode() == KeyCode.F){
            focusCameraOnModel();
        }
        if(event.getCode() == KeyCode.O) {
            isOrbiting = !isOrbiting;
            if(!scene.getSelectedModels().isEmpty()){
                orbitTarget = calculateModelCenter(scene.getModels().get(scene.getSelectedModels().get(0)));
            }else{
                orbitTarget = new Vector3(0, 0, 0);
            }
            render();
        }
    }
    private void updateCameraPosition(){
        if (!scene.getSelectedModels().isEmpty()) {
            Model model = scene.getModels().get(scene.getSelectedModels().get(0));
            Vector3 center = calculateModelCenter(model);
        }
        camera.setPosition(camera.getPosition().add(cameraOffset));
        camera.setTarget(camera.getTarget().add(cameraOffset));
        cameraOffset = new Vector3(0,0,0);
        render();
    }
    @FXML
    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.SHIFT) {
            isShiftPressed = false;
        }
    }
    @FXML
    private void handleMousePressed(MouseEvent event){
        mouseX = event.getX();
        mouseY = event.getY();
    }


    @FXML
    private void handleMouseDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            double dx = event.getX() - mouseX;
            double dy = event.getY() - mouseY;
            double sensitivity = cameraTranslateSpeed;

            Vector3 view = camera.getPosition().subtract(camera.getTarget()).normalize();
            Vector3 right = view.crossProduct(camera.getUp()).normalize();
            cameraOffset = cameraOffset.add(new Vector3(-dx * sensitivity * right.getX(), dy * sensitivity, -dx * sensitivity * right.getZ()));
            updateCameraPosition();
        }
        if (event.getButton() == MouseButton.PRIMARY) {
            double dx = event.getX() - mouseX;
            double dy = event.getY() - mouseY;
            double sensitivity = rotateSensitivity;
            if(!isOrbiting){
                if (scene.getSelectedModels().isEmpty()) {
                    rotateCamera(dx * sensitivity, dy * sensitivity, new Vector3(0, 0, 0));
                } else {
                    Model model = scene.getModels().get(scene.getSelectedModels().get(0));
                    Vector3 center = calculateModelCenter(model);
                    rotateCamera(dx * sensitivity, dy * sensitivity, center);
                }
            }else{
                rotateOrbitCamera(dx * sensitivity, dy * sensitivity);
            }
        }
        mouseX = event.getX();
        mouseY = event.getY();
        render();
    }
    private void rotateCamera(double dx, double dy, Vector3 target){
        double radius = camera.getPosition().subtract(target).magnitude();
        Vector3 newPosition = camera.getPosition().subtract(target).normalize().multiply(radius);

        Matrix4 rotationY = Matrix4.rotationY(-dx);
        Matrix4 rotationX = Matrix4.rotationX(dy);

        newPosition = rotationX.transform(rotationY.transform(newPosition)).add(target);
        camera.setPosition(newPosition);
        camera.setTarget(target);
        camera.setUp(new Vector3(0, 1, 0));
    }
    private void rotateOrbitCamera(double dx, double dy){
        cameraRotation = cameraRotation.add(new Vector3(dy, -dx, 0));
        updateOrbitCamera();
    }
    private void updateOrbitCamera(){
        double camX = cameraRotation.getX();
        double camY = cameraRotation.getY();
        Matrix4 rotationY = Matrix4.rotationY(camY);
        Matrix4 rotationX = Matrix4.rotationX(camX);
        Vector3 newPosition = rotationX.transform(rotationY.transform(new Vector3(0, 0, cameraDistance))).add(orbitTarget);
        camera.setPosition(newPosition);
        camera.setTarget(orbitTarget);
        camera.setUp(new Vector3(0, 1, 0));
    }
    @FXML
    private void loadTexture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Texture File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files", "*.jpg"));
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file != null) {
          String fileName = file.getName().toLowerCase();
            if (!fileName.endsWith(".jpg")) {
                ErrorWindow.showError("Неверный формат файла. Поддерживаются только JPG файлы.");
                 return;
            }
            try {
                Image image = new Image(file.toURI().toString());
                Texture texture = new Texture(image);
                List<Integer> selectedModels = scene.getSelectedModels();
                if (selectedModels.isEmpty()){
                    ErrorWindow.showError("No models selected");
                    return;
                }
                if (selectedModels.size() > 1){
                    ErrorWindow.showError("Only one model can have texture");
                    return;
                }

                Model model = scene.getModels().get(selectedModels.get(0));
                model.setTexture(texture);

                render();
            } catch (Exception e) {
                ErrorWindow.showError("Error loading texture: " + e.getMessage());
            }
        }
    }
    private void focusCameraOnModel(){
        List<Integer> selectedModels = scene.getSelectedModels();
        if(selectedModels.isEmpty()){
            camera.setPosition(new Vector3(0, 0, 5));
            camera.setTarget(new Vector3(0,0,0));
            render();
            return;
        }
        Model model = scene.getModels().get(selectedModels.get(0));
        Vector3 center = calculateModelCenter(model);
        cameraDistance = calculateModelRadius(model) * 2;
        camera.setPosition(center.add(new Vector3(0, 0, cameraDistance)));
        camera.setTarget(center);
        cameraRotation = new Vector3(0, 0, 0);
        render();
    }
    private double calculateModelRadius(Model model) {
        double maxRadius = 0;
        Vector3 center = calculateModelCenter(model);
        for (Vector3 vertex : model.getVertices()) {
            double distance = vertex.subtract(center).magnitude();
            maxRadius = Math.max(maxRadius, distance);
        }
        return maxRadius;
    }
    private Vector3 calculateModelCenter(Model model) {
        Vector3 center = new Vector3(0, 0, 0);

        if (model.getVertices().isEmpty()){
            return center;
        }
        for (Vector3 vertex : model.getVertices()) {
            center = center.add(vertex);
        }
        return center.multiply(1.0 / model.getVertices().size());
    }

    @FXML
    private void handleMouseScroll(javafx.scene.input.ScrollEvent event) {
        double scrollDelta = event.getDeltaY();
        double sensitivity = 0.1;
        if(!isOrbiting){
            if (scene.getSelectedModels().isEmpty()) {
                Vector3 viewDir = camera.getPosition().subtract(camera.getTarget()).normalize();
                double distance = camera.getPosition().subtract(camera.getTarget()).magnitude();
                double zoomSpeed = distance * sensitivity * 0.1;
                Vector3 newPosition = camera.getPosition().add(viewDir.multiply(-scrollDelta * zoomSpeed));
                camera.setPosition(newPosition);
            } else {
                Model model = scene.getModels().get(scene.getSelectedModels().get(0));
                Vector3 center = calculateModelCenter(model);
                Vector3 viewDir = camera.getPosition().subtract(center).normalize();
                double distance = camera.getPosition().subtract(center).magnitude();
                double zoomSpeed = distance * sensitivity * 0.1;
                Vector3 newPosition = camera.getPosition().add(viewDir.multiply(-scrollDelta * zoomSpeed));
                camera.setPosition(newPosition);
            }
        }else{
            cameraDistance += -scrollDelta * sensitivity * 0.5;
            updateOrbitCamera();
        }
        render();
    }

    @FXML
    private void loadModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open OBJ File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("OBJ files", "*.obj"));
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file != null) {
            String fileName = file.getName().toLowerCase();
            if (!fileName.endsWith(".obj")) {
                 ErrorWindow.showError("Неверный формат файла. Поддерживаются только OBJ файлы.");
                 return;
             }
            try {
                Model model = ObjReader.read(file.getAbsolutePath());
                scene.addModel(model);
                updateModelList();
                render();
            } catch (IOException e) {
                ErrorWindow.showError("Error loading model: " + e.getMessage());
            }
        }
    }

    @FXML
    private void saveModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save OBJ File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("OBJ files", "*.obj"));
        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        if (file != null) {
            try {
                List<Integer> selectedModels = scene.getSelectedModels();
                if (selectedModels.isEmpty()){
                    ErrorWindow.showError("No models selected");
                    return;
                }

                if (selectedModels.size() > 1){
                    ErrorWindow.showError("Only one model can be saved");
                    return;
                }

                Model model = scene.getModels().get(selectedModels.get(0));
                ObjWriter.write(file.getAbsolutePath(), model, true);

            } catch (IOException e) {
                ErrorWindow.showError("Error saving model: " + e.getMessage());
            }
        }
    }
    @FXML
    private void removeModel() {
        List<Integer> selectedModels = scene.getSelectedModels();
        if (selectedModels.isEmpty()) {
            ErrorWindow.showError("No models selected");
            return;
        }
        for (int i = selectedModels.size() - 1; i >= 0 ; i--) {
            scene.removeModel(scene.getModels().get(selectedModels.get(i)));
        }
        updateModelList();
        render();
    }

    private void updateModelList() {
        ObservableList<Model> observableList = FXCollections.observableList(scene.getModels());
        modelListView.setItems(observableList);
    }

    private void updateSelection() {
        if(isShiftPressed){
            if(!modelListView.getSelectionModel().getSelectedIndices().isEmpty()){
                int lastSelected = modelListView.getSelectionModel().getSelectedIndices().get(modelListView.getSelectionModel().getSelectedIndices().size() - 1);
                if(!scene.isSelected(lastSelected)){
                    scene.selectModel(lastSelected);
                }else{
                    scene.deselectModel(lastSelected);
                }

            }
        }else {
            scene.clearSelection();
            for (Integer index : modelListView.getSelectionModel().getSelectedIndices()) {
                scene.selectModel(index);
            }
        }
        updateTransformFields();
        render();
    }

    private void deleteSelectedVertices() {
        List<Integer> selectedModels = scene.getSelectedModels();
        if (selectedModels.isEmpty()) {
            ErrorWindow.showError("No models selected");
            return;
        }

        for(Integer selectedModel : selectedModels){
            Model model = scene.getModels().get(selectedModel);
            if (model.getVertices().isEmpty()){
                ErrorWindow.showError("Нет вершин в модели " + model.getName());
                continue;
            }

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Удалить вершину");
            dialog.setHeaderText("Введите индекс вершины для удаления");
            dialog.setContentText("Индекс:");

            dialog.showAndWait().ifPresent(index -> {
                try {
                    int vertexIndex = Integer.parseInt(index);
                    if(vertexIndex >= 0 && vertexIndex < model.getVertices().size()){
                        model.removeVertex(vertexIndex);
                    } else {
                        ErrorWindow.showError("Неверный индекс вершины");
                    }
                    render();
                } catch (NumberFormatException e) {
                    ErrorWindow.showError("Неверный формат индекса вершины");
                }
            });
        }
    }

    private void deleteSelectedPolygons() {
        List<Integer> selectedModels = scene.getSelectedModels();
        if (selectedModels.isEmpty()) {
            ErrorWindow.showError("Нет моделей выбрано");
            return;
        }

        for(Integer selectedModel : selectedModels){
            Model model = scene.getModels().get(selectedModel);
            if (model.getPolygons().isEmpty()){
                ErrorWindow.showError("Нет полигонов в модели " + model.getName());
                continue;
            }

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Удалить полигон");
            dialog.setHeaderText("Введите индекс полигона для удаления");
            dialog.setContentText("Индекс:");

            dialog.showAndWait().ifPresent(index -> {
                try {
                    int polygonIndex = Integer.parseInt(index);
                    if(polygonIndex >= 0 && polygonIndex < model.getPolygons().size()){
                        model.removePolygon(polygonIndex);
                    } else {
                        ErrorWindow.showError("Неверный индекс полигона");
                    }
                    render();
                } catch (NumberFormatException e) {
                    ErrorWindow.showError("Неверный формат индекса полигона");
                }
            });
        }
    }

    private void updateModelTransform(){

        List<Integer> selectedModels = scene.getSelectedModels();
        if (selectedModels.isEmpty()) {
            return;
        }

        for(Integer selectedModel : selectedModels){
            Model model = scene.getModels().get(selectedModel);
            try {
                double x = Double.parseDouble(translateX.getText());
                double y = Double.parseDouble(translateY.getText());
                double z = Double.parseDouble(translateZ.getText());

                double rx = Double.parseDouble(rotateX.getText());
                double ry = Double.parseDouble(rotateY.getText());
                double rz = Double.parseDouble(rotateZ.getText());

                double sx = Double.parseDouble(scaleX.getText());
                double sy = Double.parseDouble(scaleY.getText());
                double sz = Double.parseDouble(scaleZ.getText());

                model.getTransform().setPosition(new Vector3(x,y,z));
                model.getTransform().setRotation(new Vector3(rx,ry,rz));
                model.getTransform().setScale(new Vector3(sx,sy,sz));
            } catch (NumberFormatException e){
                // Set default values for model
                model.getTransform().setPosition(new Vector3(0, 0, 0));
                model.getTransform().setRotation(new Vector3(0, 0, 0));
                model.getTransform().setScale(new Vector3(1, 1, 1));

                // Set default values in TextField
                 translateX.setText(String.format(Locale.US, "%.1f", 0.0));
                 translateY.setText(String.format(Locale.US, "%.1f", 0.0));
                 translateZ.setText(String.format(Locale.US, "%.1f", 0.0));

                 rotateX.setText(String.format(Locale.US, "%.1f", 0.0));
                 rotateY.setText(String.format(Locale.US, "%.1f", 0.0));
                 rotateZ.setText(String.format(Locale.US, "%.1f", 0.0));

                 scaleX.setText(String.format(Locale.US, "%.1f", 1.0));
                 scaleY.setText(String.format(Locale.US, "%.1f", 1.0));
                 scaleZ.setText(String.format(Locale.US, "%.1f", 1.0));

                ErrorWindow.showError("Неверный формат числа, используются значения по умолчанию");
            }
        }
        render();
    }


   private void updateTransformFields() {
         List<Integer> selectedModels = scene.getSelectedModels();
          if (selectedModels.isEmpty()) {
            translateX.setText(String.format(Locale.US, "%.1f", 0.0));
            translateY.setText(String.format(Locale.US, "%.1f", 0.0));
            translateZ.setText(String.format(Locale.US, "%.1f", 0.0));

            rotateX.setText(String.format(Locale.US, "%.1f", 0.0));
            rotateY.setText(String.format(Locale.US, "%.1f", 0.0));
            rotateZ.setText(String.format(Locale.US, "%.1f", 0.0));

            scaleX.setText(String.format(Locale.US, "%.1f", 1.0));
            scaleY.setText(String.format(Locale.US, "%.1f", 1.0));
            scaleZ.setText(String.format(Locale.US, "%.1f", 1.0));
            return;
        }

        Model model = scene.getModels().get(selectedModels.get(0));

        translateX.setText(String.format(Locale.US, "%.1f", model.getTransform().getPosition().getX()));
        translateY.setText(String.format(Locale.US, "%.1f", model.getTransform().getPosition().getY()));
        translateZ.setText(String.format(Locale.US, "%.1f", model.getTransform().getPosition().getZ()));

        rotateX.setText(String.format(Locale.US, "%.1f", model.getTransform().getRotation().getX()));
        rotateY.setText(String.format(Locale.US, "%.1f", model.getTransform().getRotation().getY()));
        rotateZ.setText(String.format(Locale.US, "%.1f", model.getTransform().getRotation().getZ()));

        scaleX.setText(String.format(Locale.US, "%.1f", model.getTransform().getScale().getX()));
        scaleY.setText(String.format(Locale.US, "%.1f", model.getTransform().getScale().getY()));
        scaleZ.setText(String.format(Locale.US, "%.1f", model.getTransform().getScale().getZ()));
    }

    private void render() {
        renderer.setLightingEnabled(lightingEnabled);
        renderer.setTextureEnabled(textureEnabled);
        renderer.render(scene, camera);
    }
}