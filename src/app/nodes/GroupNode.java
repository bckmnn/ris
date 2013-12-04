package app.nodes;

import java.util.Map;

import app.edges.Edge;
import app.vecmath.Matrix;

public class GroupNode extends Node {

	public GroupNode() {
		super();
	}

	public GroupNode(Matrix modelMatrix, Node n, Map<String, String> data) {
		super(modelMatrix, n, data);
	}

	public GroupNode(Matrix modelMatrix, Node n) {
		super(modelMatrix, n);
	}

	public GroupNode(Matrix modelMatrix) {
		super(modelMatrix);
	}

	@Override
	public void display() {
		for (Edge e : edges) {
			if (e.isStart(this)) {
				e.getOtherNode(this).display();
			}
		}
	}
}
