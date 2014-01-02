package app.edges;

import java.util.Map;

import app.nodes.Node;

/**
 * Basic edge, brings nothing new to the table.
 * 
 * @author Constantin
 *
 */
public class DefaultEdge extends Edge {

	public DefaultEdge(Node startNode, Node endNode) {
		super(startNode, endNode);
	}
	
	public DefaultEdge(Node startNode, Node endNode, Map<String, String> data) {
		super(startNode, endNode, data);
	}
}