package app.edges;

import java.util.HashMap;
import java.util.Map;

import app.nodes.Node;

/**
 * Basic Edge that connects two nodes. The edge is direction-aware.
 * It can hold arbitrary data in a string map.
 * 
 * @author Constantin
 *
 */
public abstract class Edge implements Cloneable {
	private Node startNode;
	private Node endNode;
	private Map<String, String> data = new HashMap<String, String>();
	
	public Edge(Node startNode, Node endNode) {
		this.startNode = startNode;
		this.endNode = endNode;
		
		startNode.addEdge(this);
		endNode.addEdge(this);
	}
	
	public Edge(Node startNode, Node endNode, Map<String, String> data) {
		this.startNode = startNode;
		this.endNode = endNode;		
		this.data.putAll(data);
		
		startNode.addEdge(this);
		endNode.addEdge(this);
	}
	
	public void relocate(Node oldNode, Node newNode) {
		oldNode.removeEdge(this);
		newNode.addEdge(this);
		
		if (startNode == oldNode) {
		    startNode = newNode;
		} else {
		    endNode = newNode;
		}
	}
	
	public void addData(Map<String, String> newData) {
		data.putAll(newData);
	}
	
	public Node getOtherNode(Node self) {
		if (startNode.equals(self)) {
			return endNode;
		} else {
			return startNode;
		}
	}
	
	public boolean isStart(Node self) {
		return startNode.equals(self);
	}
	
	public Map<String, String> getData() {
	    return data;
	}
	
	public Edge clone() {
	    try {
            return (Edge) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	    return null;
	}
}