package core;

import math.Matrix4;
import math.Vector3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SceneTest {

    @Test
    public void testAddAndRemoveModel() {
        Scene scene = new Scene();
        Model model1 = new Model("Model1");
        Model model2 = new Model("Model2");

        scene.addModel(model1);
        scene.addModel(model2);

        assertEquals(2, scene.getModels().size(), "Model count should be 2 after adding models");

        scene.removeModel(model1);
        assertEquals(1, scene.getModels().size(), "Model count should be 1 after removing a model");
        assertFalse(scene.getModels().contains(model1), "Scene should not contain removed model");
    }

    @Test
    public void testSelectAndDeselectModel() {
        Scene scene = new Scene();
        Model model1 = new Model("Model1");
        Model model2 = new Model("Model2");

        scene.addModel(model1);
        scene.addModel(model2);

        scene.selectModel(0);
        assertTrue(scene.isSelected(0), "Model at index 0 should be selected");

        scene.deselectModel(0);
        assertFalse(scene.isSelected(0), "Model at index 0 should not be selected after deselection");
    }

    @Test
    public void testSelectAllModels() {
        Scene scene = new Scene();
        Model model1 = new Model("Model1");
        Model model2 = new Model("Model2");

        scene.addModel(model1);
        scene.addModel(model2);

        scene.selectAll();
        assertEquals(2, scene.getSelectedModels().size(), "All models should be selected");
    }

    @Test
    public void testClearSelection() {
        Scene scene = new Scene();
        Model model1 = new Model("Model1");
        Model model2 = new Model("Model2");

        scene.addModel(model1);
        scene.addModel(model2);

        scene.selectAll();
        assertEquals(2, scene.getSelectedModels().size(), "All models should be selected before clearing");

        scene.clearSelection();
        assertTrue(scene.getSelectedModels().isEmpty(), "Selection should be empty after clearing");
    }
}
