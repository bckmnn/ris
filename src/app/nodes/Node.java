package app.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import app.edges.DefaultEdge;
import app.edges.Edge;
import app.vecmath.Matrix;
import app.vecmathimp.FactoryDefault;

public abstract class Node implements Cloneable {
    private Matrix modelMatrix, worldTrafo;
    protected List<Edge> edges = new ArrayList<Edge>();
    public String name;

    public Matrix getWorldTransform() {
        return worldTrafo;
    }

    protected Node() {

    }

    protected Node(Matrix modelMatrix) {
        setLocalTransform(modelMatrix);
        updateWorldTransform(FactoryDefault.vecmath.identityMatrix());
    }

    protected Node(Matrix modelMatrix, Node n) {
        new DefaultEdge(n, this);

        setLocalTransform(modelMatrix);
        updateWorldTransform(FactoryDefault.vecmath.identityMatrix());
    }

    protected Node(Matrix modelMatrix, Node n, Map<String, String> data) {
        new DefaultEdge(this, n, data);

        setLocalTransform(modelMatrix);
        updateWorldTransform(FactoryDefault.vecmath.identityMatrix());
    }

    public Matrix getLocalTransform() {
        return modelMatrix;
    }

    public void setLocalTransform(Matrix modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public void updateWorldTransform(Matrix previousTrafo) {
        worldTrafo = previousTrafo.mult(getLocalTransform());
    }

    public void updateWorldTransform() {
        worldTrafo = getLocalTransform();
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public void removeEdge(Edge e) {
        edges.remove(e);
    }

    public Edge appendTo(Node n) {
        return new DefaultEdge(n, this);
    }

    public Edge appendTo(Node n, Map<String, String> data) {
        return new DefaultEdge(n, this, data);
    }

    public Edge append(Node n) {
        return new DefaultEdge(this, n);
    }

    public Edge append(Node n, Map<String, String> data) {
        return new DefaultEdge(this, n, data);
    }

    public Map<String, String> getData(Node n) {
        for (Edge e : edges) {
            if (e.getOtherNode(this) == n) return e.getData();
        }

        return new HashMap<String, String>();
    }

    public abstract void display();
}