// core/Transform.java
package core;


import math.Matrix4;
import math.Vector3;

public class Transform {
    private Vector3 position = new Vector3(0, 0, 0);
    private Vector3 scale = new Vector3(1, 1, 1);
    private Vector3 rotation = new Vector3(0, 0, 0);
    private Matrix4 modelMatrix = new Matrix4();

    public Transform() {
        updateModelMatrix();
    }
    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
        updateModelMatrix();
    }

    public Vector3 getScale() {
        return scale;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
        updateModelMatrix();
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
        updateModelMatrix();
    }

    public Matrix4 getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4 modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    private void updateModelMatrix() {
        Matrix4 translationMatrix = Matrix4.translation(position.getX(), position.getY(), position.getZ());
        Matrix4 scaleMatrix = Matrix4.scaling(scale.getX(), scale.getY(), scale.getZ());
        Matrix4 rotationXMatrix = Matrix4.rotationX(rotation.getX());
        Matrix4 rotationYMatrix = Matrix4.rotationY(rotation.getY());
        Matrix4 rotationZMatrix = Matrix4.rotationZ(rotation.getZ());

        modelMatrix = translationMatrix.multiply(rotationXMatrix).multiply(rotationYMatrix).multiply(rotationZMatrix).multiply(scaleMatrix);
    }
}