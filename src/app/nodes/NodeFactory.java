package app.nodes;

import java.util.HashMap;
import java.util.Map;

import app.nodes.camera.Camera;
import app.nodes.shapes.Cube;
import app.shader.Shader;
import app.vecmath.Matrix;

/**
 * Creates and keeps track of created nodes for easy modification
 * 
 * @author Constantin
 *
 */
public class NodeFactory {
	
	// Easy access
	public static final NodeFactory nodeFactory = new NodeFactory();
	
	public GroupNode groupNode(String id) {
		return new GroupNode(id);
	}
	
	public GroupNode groupNode(String id, Matrix modelMatrix) {
		return new GroupNode(id, modelMatrix);
	}
	
	public GroupNode groupNode(String id, Matrix modelMatrix, Node node) {
		GroupNode n = new GroupNode(id, modelMatrix, node);
		return n;
	}
	
	public GroupNode groupNode(String id, Matrix modelMatrix, Node node,
			Map<String, String> data) {
		GroupNode n = new GroupNode(id, modelMatrix, node, data);
		return n;
	}
	
	public Camera camera(String id) {
		Camera n = new Camera(id);
		return n;
	}
	
	public Cube cube(String id, Shader shader) {
		Cube n = new Cube(id, shader);
		return n;
	}

}
