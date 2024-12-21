// core/Model.java
package core;

import math.Vector2;
import math.Vector3;
import render.Texture;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Vector3> vertices = new ArrayList<>();
    private List<List<Integer>> polygons = new ArrayList<>();
     private List<Vector2> textureVertices = new ArrayList<>();
    private String name;
    private Transform transform = new Transform();
  private Texture texture;


    public Model(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Vector3> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3> vertices) {
        this.vertices = vertices;
    }

    public List<List<Integer>> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<List<Integer>> polygons) {
        this.polygons = polygons;
    }

    public List<Vector2> getTextureVertices() {
        return textureVertices;
    }

    public void setTextureVertices(List<Vector2> textureVertices) {
        this.textureVertices = textureVertices;
    }


    public void addVertex(Vector3 vertex) {
      vertices.add(vertex);
    }

    public void addPolygon(List<Integer> polygon) {
      polygons.add(polygon);
    }
      public void addTextureVertex(Vector2 textureVertex) {
          textureVertices.add(textureVertex);
      }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getPolygonCount() {
      return polygons.size();
    }

    //Методы для удаления вершин и полигонов
    public void removeVertex(int index) {
        if (index >= 0 && index < vertices.size()) {
            vertices.remove(index);
        }
    }

    public void removePolygon(int index) {
        if (index >= 0 && index < polygons.size()) {
            polygons.remove(index);
        }
    }
    public Transform getTransform() {
      return transform;
    }

    public void setTransform(Transform transform) {
      this.transform = transform;
    }
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    @Override
    public String toString() {
        return name;
    }
}