package core;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final List<Model> models = new ArrayList<>();
    private final List<Integer> selectedModels = new ArrayList<>();

    public List<Model> getModels() {
        return models;
    }

    public void addModel(Model model) {
        models.add(model);
    }

    public void removeModel(Model model) {
        models.remove(model);
        selectedModels.remove(Integer.valueOf(models.indexOf(model)));
    }


    public void clearSelection() {
        selectedModels.clear();
    }

    public List<Integer> getSelectedModels() {
        return selectedModels;
    }

    public void selectModel(int index) {
        if (index >= 0 && index < models.size() && !selectedModels.contains(index)) {
            selectedModels.add(index);
        }
    }

    public void deselectModel(int index) {
        selectedModels.remove(Integer.valueOf(index));
    }

    public void selectAll() {
        selectedModels.clear();
        for (int i = 0; i < models.size(); i++) {
            selectedModels.add(i);
        }
    }

    public boolean isSelected(int index) {
        return selectedModels.contains(index);
    }
}