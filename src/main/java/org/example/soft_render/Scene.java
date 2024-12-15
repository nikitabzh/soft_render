package com.cgvsu.render_engine;

import java.util.ArrayList;

public class Scene {
    private ArrayList<Camera> cameras = new ArrayList<>();
    private int activeCameraIndex = 0;

    public void addCamera(Camera camera) {
        cameras.add(camera);
    }

    public Camera getActiveCamera() {
        return cameras.get(activeCameraIndex);
    }

    public void switchToNextCamera() {
        activeCameraIndex = (activeCameraIndex + 1) % cameras.size();
    }
}